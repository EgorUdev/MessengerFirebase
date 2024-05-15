package com.example.messengerfirebase;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private static final String EXTRA_CURRENT_USER_ID = "current_id";
    private static final String EXTRA_OTHER_USER_ID = "other_id";
    private TextView textViewTitle;
    private View viewChatStatus;
    private RecyclerView rvChat;
    private EditText etMessageInput;
    private ImageView imageViewSendMessage;

    private MessagesAdapter messagesAdapter;
    private String currentUserId;
    private String otherUserId;
    private boolean isOnline;

    private ChatViewModel chatViewModel;
    private ChatViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        initViews();
        currentUserId = getIntent().getStringExtra(EXTRA_CURRENT_USER_ID);
        otherUserId = getIntent().getStringExtra(EXTRA_OTHER_USER_ID);
        viewModelFactory = new ChatViewModelFactory(currentUserId, otherUserId);
        chatViewModel = new ViewModelProvider(this, viewModelFactory)
                .get(ChatViewModel.class);
        messagesAdapter = new MessagesAdapter(currentUserId);
        rvChat.setAdapter(messagesAdapter);
        observeViewModel();
        imageViewSendMessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new Message(
                        etMessageInput.getText().toString().trim(),
                        currentUserId,
                        otherUserId
                );
                chatViewModel.sendMessage(message);
            }
        });
    }

    private void observeViewModel() {
        chatViewModel.getOtherUser().observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                String userInfo = String.format("%s %s", user.getName(), user.getLastName());
                textViewTitle.setText(userInfo);
                int backgroundResId = 0;
                if (user.isOnline()) {
                    backgroundResId = R.drawable.circle_green;
                } else {
                    backgroundResId = R.drawable.circle_red;
                }
                Drawable background = ContextCompat.getDrawable(ChatActivity.this, backgroundResId);
                viewChatStatus.setBackground(background);
            }
        });

        chatViewModel.getMessages().observe(this, new Observer<List<Message>>() {
            @Override
            public void onChanged(List<Message> messages) {
                messagesAdapter.setMessages(messages);
            }
        });

        chatViewModel.getError().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String errorMessage) {
                Toast.makeText(ChatActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

        chatViewModel.getMessageSent().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean sent) {
                if (sent) {
                    etMessageInput.setText("");
                }
            }
        });
    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textViewTitle);
        viewChatStatus = findViewById(R.id.viewChatStatus);
        rvChat = findViewById(R.id.recyclerViewChat);
        etMessageInput = findViewById(R.id.editTextMessageInput);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);
    }

    @Override
    protected void onResume() {
        super.onResume();
        chatViewModel.setUserOnline(true);
    }

    @Override
    protected void onPause() {
        super.onPause();
        chatViewModel.setUserOnline(false);
    }

    public static Intent newIntent(Context context, String currentUserId, String otherUserId) {
        Intent intent = new Intent(context, ChatActivity.class);
        intent.putExtra(EXTRA_CURRENT_USER_ID, currentUserId);
        intent.putExtra(EXTRA_OTHER_USER_ID, otherUserId);
        return intent;
    }
}