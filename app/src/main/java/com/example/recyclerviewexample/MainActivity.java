package com.example.recyclerviewexample;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.jetbrains.annotations.NotNull;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {

    private final LinkedList<String> mWordList = new LinkedList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        for (int i = 0; i < 20; i++) {
            mWordList.add("Word " + i);
        }

        // ui related code
        FloatingActionButton fab = findViewById(R.id.fab);
        RecyclerView recyclerWord= findViewById(R.id.recyclerWord);
        recyclerWord.setLayoutManager(new LinearLayoutManager(this));
        WordAdapter adapter = new WordAdapter(this, mWordList);
        recyclerWord.setAdapter(adapter);
        fab.setOnClickListener(view -> {
            int size = mWordList.size();
            mWordList.addLast("Word " + size);
            adapter.notifyItemInserted(size);
            recyclerWord.smoothScrollToPosition(size);
        });


    }

    class WordAdapter extends RecyclerView.Adapter<WordAdapter.WordViewHolder> {

        private LinkedList<String> mWordList;
        private LayoutInflater inflater;

        WordAdapter(Context ctx, LinkedList<String> wordlist) {
            this.mWordList = wordlist;
            inflater = LayoutInflater.from(ctx);
        }

        @NonNull
        @NotNull
        @Override
        public WordViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
            View v = inflater.inflate(R.layout.wordlist_item, parent,false);
            return new WordViewHolder(v,this);
        }

        @Override
        public void onBindViewHolder(@NonNull @NotNull WordAdapter.WordViewHolder holder, int position) {
            String word = mWordList.get(position);
            holder.wordText.setText(word);

        }

        @Override
        public int getItemCount() {
            return mWordList.size();
        }

        class WordViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            WordAdapter adapter;
            TextView wordText;

            public WordViewHolder(@NonNull View itemView, WordAdapter adapter) {
                super(itemView);
                this.adapter = adapter;
                wordText = itemView.findViewById(R.id.word_text);
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View view) {
                int currPos = getLayoutPosition();
                String word = mWordList.get(currPos);
                mWordList.set(currPos,word+" clicked !");
                adapter.notifyDataSetChanged();
            }
        }
    }
}