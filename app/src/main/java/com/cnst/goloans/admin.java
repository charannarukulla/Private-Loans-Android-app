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

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class admin extends AppCompatActivity {
    String countq;
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
Button download;
    private FirebaseFirestore db;
    private FirestoreRecyclerAdapter adapter;
    LinearLayoutManager linearLayoutManager;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        ButterKnife.bind(this);
        init();
        download=findViewById(R.id.download);
        getFriendList();


    }

    private void init(){
        linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        friendList.setLayoutManager(linearLayoutManager);
        db = FirebaseFirestore.getInstance();
    }
    @SuppressLint("SetTextI18n")
    private void getFriendList(){
        Query query = db.collection("pending");

        final FirestoreRecyclerOptions<Response> response = new FirestoreRecyclerOptions.Builder<Response>()
                .setQuery(query, Response.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Response, FriendsHolder>(response) {
            @SuppressLint("SetTextI18n")
            @Override
            public void onBindViewHolder(final FriendsHolder holder, final int position, final Response model) {
                progressBar.setVisibility(View.GONE);
                holder.textName.setText(model.getFirstname());
                holder.textTitle.setText(model.getEmail());
                holder.textCompany.setText(model.getPhone());
                countq= String.valueOf(adapter.getItemCount());
                TextView textView=findViewById(R.id.textView3);
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


                File file = new File(getExternalFilesDir(null),"golans pending"+fname+".xls");
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
                textView.setText("Total no of pending applications:"+adapter.getItemCount());


holder.view.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder = new AlertDialog.Builder(admin.this);
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
                        String uid= model.getUid();
                        String fcmid=model.getFcmid();
                        Toast.makeText(admin.this, "please wait", Toast.LENGTH_SHORT).show();
                        Map<String, Object> user1 = new HashMap<>();

                        user1.put("aadar",response.getSnapshots().getSnapshot(position).getString("aadar"));
                        user1.put("city",response.getSnapshots().getSnapshot(position).getString("city"));
                        user1.put("dob",response.getSnapshots().getSnapshot(position).getString("dob"));
                        user1.put("email",response.getSnapshots().getSnapshot(position).getString("email"));
                        user1.put("emptype",response.getSnapshots().getSnapshot(position).getString("emptype"));
                        user1.put("firstname",response.getSnapshots().getSnapshot(position).getString("firstname"));
                        user1.put("lastname",response.getSnapshots().getSnapshot(position).getString("lastname"));
                        user1.put("gender",response.getSnapshots().getSnapshot(position).getString("gender"));
                        user1.put("income",response.getSnapshots().getSnapshot(position).getString("income"));
                        user1.put("pan",response.getSnapshots().getSnapshot(position).getString("pan"));
                        user1.put("phone",response.getSnapshots().getSnapshot(position).getString("phone"));
                        user1.put("pincode",response.getSnapshots().getSnapshot(position).getString("pincode"));
                        user1.put("previousloan",response.getSnapshots().getSnapshot(position).getString("previousloan"));
                        user1.put("rate",response.getSnapshots().getSnapshot(position).getString("rate"));
                        user1.put("share",response.getSnapshots().getSnapshot(position).getString("share"));
                        user1.put("source",response.getSnapshots().getSnapshot(position).getString("source"));
                        user1.put("typeofloan",response.getSnapshots().getSnapshot(position).getString("typeofloan"));
                        db.collection("approved").document(response.getSnapshots().getSnapshot(position).getString("uid")).set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {


                                firebaseFirestore.collection("users").document(response.getSnapshots().getSnapshot(position).getString("uid")).update("status","approved").addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        firebaseFirestore.collection("pending").document(response.getSnapshots().getSnapshot(position).getString("uid")).delete();
                                        WebView webView=findViewById(R.id.notifi);
                                        webView.setVisibility(View.INVISIBLE);
                                        
                                        String url="http://goloans.epizy.com/appnotifiserver.php?send_notification&token="+response.getSnapshots().getSnapshot(position).getString("fcmid")+"&body=APPLICATION APPROVED";
                                        webView.loadUrl(url);
                                        Toast.makeText(admin.this, ""+url, Toast.LENGTH_LONG).show();
                                        WebSettings webSettings = webView.getSettings();
                                        webSettings.setJavaScriptEnabled(true);

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
        Map<String, Object> user1 = new HashMap<>();
        Toast.makeText(admin.this, "please wait", Toast.LENGTH_SHORT).show();
        user1.put("aadar",response.getSnapshots().getSnapshot(position).getString("aadar"));
        user1.put("city",response.getSnapshots().getSnapshot(position).getString("city"));
        user1.put("dob",response.getSnapshots().getSnapshot(position).getString("dob"));
        user1.put("email",response.getSnapshots().getSnapshot(position).getString("email"));
        user1.put("emptype",response.getSnapshots().getSnapshot(position).getString("emptype"));
        user1.put("firstname",response.getSnapshots().getSnapshot(position).getString("firstname"));
        user1.put("lastname",response.getSnapshots().getSnapshot(position).getString("lastname"));
        user1.put("gender",response.getSnapshots().getSnapshot(position).getString("gender"));
        user1.put("income",response.getSnapshots().getSnapshot(position).getString("income"));
        user1.put("pan",response.getSnapshots().getSnapshot(position).getString("pan"));
        user1.put("phone",response.getSnapshots().getSnapshot(position).getString("phone"));
        user1.put("pincode",response.getSnapshots().getSnapshot(position).getString("pincode"));
        user1.put("previousloan",response.getSnapshots().getSnapshot(position).getString("previousloan"));
        user1.put("rate",response.getSnapshots().getSnapshot(position).getString("rate"));
        user1.put("share",response.getSnapshots().getSnapshot(position).getString("share"));
        user1.put("source",response.getSnapshots().getSnapshot(position).getString("source"));
        user1.put("typeofloan",response.getSnapshots().getSnapshot(position).getString("typeofloan"));
        db.collection("rejected").document(response.getSnapshots().getSnapshot(position).getString("uid")).set(user1).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {


        firebaseFirestore.collection("users").document(response.getSnapshots().getSnapshot(position).getString("uid")).update("status","rejected").addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                firebaseFirestore.collection("pending").document(response.getSnapshots().getSnapshot(position).getString("uid")).delete();
                WebView webView=findViewById(R.id.notifi);
                webView.setVisibility(View.INVISIBLE);
                String url="http://goloans.epizy.com/appnotifiserver.php?send_notification&token="+response.getSnapshots().getSnapshot(position).getString("fcmid")+"&body=APPLICATION REJECTED";
                webView.loadUrl(url);
                WebSettings webSettings = webView.getSettings();
                webSettings.setJavaScriptEnabled(true);
            }
        });

    }
});
    }
});
            }

            @Override
            public FriendsHolder onCreateViewHolder(ViewGroup group, int i) {
                View view = LayoutInflater.from(group.getContext())
                        .inflate(R.layout.list_item, group, false);

                return new FriendsHolder(view);
            }

            @Override
            public void onError(FirebaseFirestoreException e) {
                Log.e("error", e.getMessage());
            }
        };

        adapter.notifyDataSetChanged();
        friendList.setAdapter(adapter);
    }



    public static class FriendsHolder extends RecyclerView.ViewHolder {
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
