package com.example.roomdbcrud;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public ListView listview;
    ArrayAdapter arrayAdapter;
    public Button btnadd, btnremove;
    public EditText edtInput;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppDatabase db = Room.databaseBuilder(getApplicationContext(),
                AppDatabase.class, "UserDB").allowMainThreadQueries().build();

        UserDao userDao = db.userDao();
        User user1 = new User(1, "Đỗ Anh Bôn");
        User user2 = new User(2, "Hoàng Quốc Cường");
        User user3 = new User(3, "Phạm Minh Dũng");
        User user4 = new User(4, "Châu Hoàng Duy");
        User user5 = new User(5, "Trần Nhật Duy");
        User user6 = new User(6, "Nguyễn Đình Hảo");
        userDao.insertAll(user1, user2, user3, user4, user5, user6);

        List<User> users = userDao.getAll();

        listview = findViewById(R.id.listview);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, users);
        listview.setAdapter(arrayAdapter);

        edtInput = findViewById(R.id.edtInput);
        btnadd = findViewById(R.id.btnadd);
        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0, count = users.size();
                String name = edtInput.getText().toString();
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getName() != name && edtInput.length() != 0) {
                        User u = new User(count + 1, name);
                        userDao.insertAll(u);
                        arrayAdapter.add(u);
                        Toast.makeText(MainActivity.this, "Thêm user thành công!!!", Toast.LENGTH_SHORT).show();
                        flag++;
                        break;
                    }
                    else
                        flag = 0;
                }
                if(flag == 0)
                    Toast.makeText(MainActivity.this, "Tên user không hợp lệ hoặc bị trùng!!!", Toast.LENGTH_SHORT).show();
            }
        });

        btnremove = findViewById(R.id.btnremove);
        btnremove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int flag = 0;
                String name = edtInput.getText().toString();
                User user = null;
                for (int i = 0; i < users.size(); i++) {
                    if (users.get(i).getName().equals(name)) {
                        user = users.get(i);
                        userDao.delete(user);
                        users.remove(i);
                        arrayAdapter.notifyDataSetChanged();
                        Toast.makeText(MainActivity.this, "Xóa user thành công!!!", Toast.LENGTH_SHORT).show();
                        flag++;
                        break;
                    } else
                        flag = 0;
                }
                if (flag == 0)
                    Toast.makeText(MainActivity.this, "Tên user không hợp lệ!!!", Toast.LENGTH_SHORT).show();
            }
        });



    }
}