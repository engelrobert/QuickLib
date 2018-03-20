package com.example.ro_en.quicklib;

import android.animation.Animator;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

public class DisplayBookListActivity extends NavigationDrawerActivity {

    FloatingActionButton fabMenuButton, fabAddBook, fabScanBook;
    LinearLayout fabLayout1, fabLayout2;
    View fabBGLayout;
    boolean isFABOpen=false;

    private List<Book> bookList = new ArrayList<>();
    private RecyclerView bookListRecyclerView;
    private BookListAdapter bookListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_display_book_list);

        //navigation Drawer
        getLayoutInflater().inflate(R.layout.activity_display_book_list, frameLayout);

        fabLayout1= (LinearLayout) findViewById(R.id.fabLayout_add_book);
        fabLayout2= (LinearLayout) findViewById(R.id.fabLayout_scan_book);
        fabMenuButton = (FloatingActionButton) findViewById(R.id.fab_menu);
        fabAddBook = (FloatingActionButton) findViewById(R.id.fab_add_book);
        fabScanBook = (FloatingActionButton) findViewById(R.id.fab_scan_book);

        final AlphaAnimation alphaAnimation = new AlphaAnimation(1F, 0.5F);

        fabAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AddBookActivity.class));
                v.startAnimation(alphaAnimation);
            }
        });

        fabScanBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), BarcodeScannerActivity.class));
                v.startAnimation(alphaAnimation);
            }
        });
        fabBGLayout = (View) findViewById(R.id.fabBGLayout);

        fabMenuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!isFABOpen){
                    showFABMenu();
                }else{
                    closeFABMenu();
                }
            }
        });

        fabBGLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                closeFABMenu();
            }
        });

        bookListRecyclerView = (RecyclerView) findViewById(R.id.display_book_list_recycler_View);

        bookListAdapter = new BookListAdapter(bookList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        bookListRecyclerView.setLayoutManager(mLayoutManager);
        bookListRecyclerView.setItemAnimator(new DefaultItemAnimator());
        bookListRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        bookListRecyclerView.setAdapter(bookListAdapter);

        prepareBookData();
    }

    private void prepareBookData() {
        Book book = new Book("hallo","1234567891",
                "thomas", "schaffran","12-06-1995",
                "maulbronn", 155);
        //TODO: hier kommen später die Daten rein
        bookList.add(book);

        book = new Book();
        bookList.add(book);

        bookListAdapter.notifyDataSetChanged();
    }

    private void showFABMenu(){
        isFABOpen=true;
        fabLayout1.setVisibility(View.VISIBLE);
        fabLayout2.setVisibility(View.VISIBLE);
        fabBGLayout.setVisibility(View.VISIBLE);

        fabMenuButton.animate().rotationBy(135);
        fabLayout1.animate().translationY(-getResources().getDimension(R.dimen.standard_55));
        fabLayout2.animate().translationY(-getResources().getDimension(R.dimen.standard_100));
    }

    private void closeFABMenu(){
        isFABOpen=false;
        fabBGLayout.setVisibility(View.GONE);
        fabMenuButton.animate().rotationBy(-135);
        fabLayout1.animate().translationY(0);
        fabLayout2.animate().translationY(0).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if(!isFABOpen){
                    fabLayout1.setVisibility(View.GONE);
                    fabLayout2.setVisibility(View.GONE);
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        if(isFABOpen){
            closeFABMenu();
        }else{
            super.onBackPressed();
        }
    }
}