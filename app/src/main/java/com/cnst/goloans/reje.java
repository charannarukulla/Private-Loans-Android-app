package com.cnst.goloans;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.ButterKnife;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Objects;

public class reje extends AppCompatActivity {
    FirebaseFirestore firebaseFirestore=FirebaseFirestore.getInstance();
    @BindView(R.id.progress_bar)
    ProgressBar progressBar;
    @Nullable
    @BindView(R.id.approve)
    Button approve;

    @Nullable
    @BindView(R.id.reject)

    Button reject;
    @Nullable
    @BindView(R.id.view)
    Button view;
    @BindView(R.id.friend_list)
    RecyclerView friendList;

    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conn);

        ButterKnife.bind(this);
        init();
        getFriendList();
    }
    private void init(){
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        friendList.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();
    }
    private void getFriendList(){
        Query query = db.collection("rejected");

        final FirestoreRecyclerOptions<Response> response = new FirestoreRecyclerOptions.Builder<Response>()
                .setQuery(query, Response.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Response, admin.FriendsHolder>(response) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onBindViewHolder(final admin.FriendsHolder holder, final int position, final Response model) {
                progressBar.setVisibility(View.GONE);
                holder.textName.setText(model.getFirstname());
                holder.textTitle.setText(model.getEmail());
                holder.textCompany.setText(model.getPhone());
                TextView textView=findViewById(R.id.textView3);
                Button download=findViewById(R.id.download);
                download.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss");
                        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                        String fname=sdf.format(timestamp);

                        Workbook wb=new HSSFWorkbook();
                        Cell cell=null;

                        Sheet sh=null;
                        sh=wb.createSheet("new");
                        for(int i=1;i<=adapter.getItemCount();i++) {
                            Row row=sh.createRow(0);
                            cell = row.createCell(0);
                            cell.setCellValue("NAME");



                            cell = row.createCell(1);
                            cell.setCellValue("AADAR");
                            cell=row.createCell(2);
                            cell.setCellValue("CITY");
                            cell=row.createCell(3);
                            cell.setCellValue("DOB");
                            cell=row.createCell(4);
                            cell.setCellValue("EMAIL");
                            cell=row.createCell(5);
                            cell.setCellValue("GENDER");
                            cell=row.createCell(6);
                            cell.setCellValue("INCOME");
                            cell=row.createCell(7);
                            cell.setCellValue("EMP TYPE");
                            cell=row.createCell(8);
                            cell.setCellValue("PAN");
                            cell=row.createCell(9);
                            cell.setCellValue("PHONE");
                            cell=row.createCell(10);
                            cell.setCellValue("PINCODE");
                            cell=row.createCell(11);
                            cell.setCellValue("PREVIOUS LOAN");
                            cell=row.createCell(12);
                            cell.setCellValue("RATE");
                            cell=row.createCell(13);
                            cell.setCellValue("TYPE OF LOAN");
                            cell=row.createCell(14);
                            cell.setCellValue("WAY OF INCOME");
                            cell=row.createCell(15);
                            cell.setCellValue("CAN WE SHARE YOUR DETAILS");
                            row = sh.createRow(i);
                            cell = row.createCell(0);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("firstname")+" "+response.getSnapshots().getSnapshot(i-1).getString("lastname"));



                            cell = row.createCell(1);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("aadar"));
                            cell=row.createCell(2);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("city"));
                            cell=row.createCell(3);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("dob"));
                            cell=row.createCell(4);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("email"));
                            cell=row.createCell(5);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("gender"));
                            cell=row.createCell(6);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("income"));
                            cell=row.createCell(7);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("emptype"));
                            cell=row.createCell(8);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("pan"));
                            cell=row.createCell(9);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("phone"));
                            cell=row.createCell(10);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("pincode"));
                            cell=row.createCell(11);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("previousloan"));
                            cell=row.createCell(12);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("rate"));
                            cell=row.createCell(13);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("typeofloan"));
                            cell=row.createCell(14);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("source"));
                            cell=row.createCell(15);
                            cell.setCellValue(response.getSnapshots().getSnapshot(i-1).getString("share"));
                        }


                        File file = new File(getExternalFilesDir(null),"golans rejected"+fname+".xls");
                        FileOutputStream outputStream =null;

                        try {
                            outputStream=new FileOutputStream(file);
                            wb.write(outputStream);
                            Toast.makeText(getApplicationContext(),"FILE SAVED",Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(Intent.ACTION_VIEW);
                            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                            intent.setDataAndType(FileProvider.getUriForFile(getApplicationContext(),"com.cnst.goloans.fileprovider", file), "application/vnd.ms-excel");
                            startActivity(intent);
                        } catch (java.io.IOException e) {
                            e.printStackTrace();

                            Toast.makeText(getApplicationContext(),"FILE NOT SAVED",Toast.LENGTH_LONG).show();

                        }

                    }
                });
                textView.setText("Total no of REJECTED applications:"+adapter.getItemCount());



                holder.view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(reje.this);
                        builder.setTitle("APPLICATION OF "+response.getSnapshots().getSnapshot(position).getString("firstname"));
                        builder.setMessage("NAME:"+" "+response.getSnapshots().getSnapshot(position).getString("firstname")+" "+response.getSnapshots().getSnapshot(position).getString("lastname")
                                +"\n"+"AADAR NUMBER: "+response.getSnapshots().getSnapshot(position).getString("aadar")+"\n"+"CITY: "+response.getSnapshots().getSnapshot(position).getString("city")
                                +"\n"+"Date Of Birth: "+response.getSnapshots().getSnapshot(position).getString("dob")+"\n"+"EMAIL: "+response.getSnapshots().getSnapshot(position).getString("email")
                                +"\n"+"GENDER: "+response.getSnapshots().getSnapshot(position).getString("gender")+"\n"+"INCOME: "+response.getSnapshots().getSnapshot(position).getString("income")
                                +"\n"+"EMPLOYMENT TYPE: "+response.getSnapshots().getSnapshot(position).getString("emptype")+"\n"+"PAN: "+response.getSnapshots().getSnapshot(position).getString("pan")
                                +"\n"+"PHONE: "+response.getSnapshots().getSnapshot(position).getString("phone")+"\n"+"PINCODE: "+response.getSnapshots().getSnapshot(position).getString("pincode")
                                +"\n"+"PREVIOUS LOAN: "+response.getSnapshots().getSnapshot(position).getString("previousloan")+"\n"+"RATE: "+response.getSnapshots().getSnapshot(position).getString("rate")
                                +"\n"+"TYPE OF LOAN: "+response.getSnapshots().getSnapshot(position).getString("typeofloan")+"\n"+"METHOD OF MONTHLY INCOME: "+response.getSnapshots().getSnapshot(position).getString("source")
                                +"\n"+"INFORMATION SHARING PERMISSION: "+response.getSnapshots().getSnapshot(position).getString("share")

                        );
                        AlertDialog dialog = builder.create();
                        builder.show();
                    }
                });
                holder.approve.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        final String uid= model.getUid();
                        getSnapshots().getSnapshot(position).getReference().delete();
                        final String fcmid=model.getFcmid();

                        Task<Void> documentReference= firebaseFirestore.collection("users").document(Objects.requireNonNull(getSnapshots().getSnapshot(position).getString("uid"))).update("status","approved").addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {

                                WebView webView=findViewById(R.id.notifi);
                                webView.setVisibility(View.INVISIBLE);
                                String url="http://goloans.epizy.com/appnotifiserver.php?send_notification&token="+response.getSnapshots().getSnapshot(position).getString("fcmid")+"&body=APPLICATION APPROVED";
                                webView.loadUrl(url);
                                WebSettings webSettings = webView.getSettings();
                                webSettings.setJavaScriptEnabled(true);
                                firebaseFirestore.collection("pending").document(uid).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {



                                    }
                                });
                            }
                        });



                    }
                });

                holder.reject.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String uid= model.getUid();
                        String fcmid=model.getFcmid();
                        firebaseFirestore.collection("users").document(uid).update("status","rejected");
                        firebaseFirestore.collection("pending").document(uid).delete();
                        WebView webView=findViewById(R.id.notifi);
                        webView.setVisibility(View.INVISIBLE);
                        String url="http://goloans.epizy.com/appnotifiserver.php?send_notification&token="+fcmid+"&body=APPLICATION REJECTED";
                        webView.loadUrl(url);
                        WebSettings webSettings = webView.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                    }
                });

            }

            @Override
            public admin.FriendsHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.list_approv, group, false);

                return new admin.FriendsHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        friendList.setAdapter(adapter);
    }

    public class FriendsHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.name)
        TextView textName;
        @BindView(R.id.title)
        TextView textTitle;
        @BindView(R.id.company)
        TextView textCompany;

        @BindView(R.id.approve)
        Button approve;
        @BindView(R.id.reject)
        Button reject;
        @BindView( R.id.view)
        Button view;

        public FriendsHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }


}
