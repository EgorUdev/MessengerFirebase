package com.example.messengerfirebase;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

public class ChatActivity extends AppCompatActivity {

    private TextView textViewTitle;
    private View viewChatStatus;
    private RecyclerView rvChat;
    private EditText etMessageInput;
    private ImageView imageViewSendMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_chat);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initViews();

    }

    private void initViews() {
        textViewTitle = findViewById(R.id.textViewTitle);
        viewChatStatus = findViewById(R.id.viewChatStatus);
        rvChat = findViewById(R.id.recyclerViewChat);
        etMessageInput = findViewById(R.id.editTextMessageInput);
        imageViewSendMessage = findViewById(R.id.imageViewSendMessage);
    }
}