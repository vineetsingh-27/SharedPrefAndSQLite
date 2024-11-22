package com.example.sharedpreferenceandsqlite.admin;

import com.example.sharedpreferenceandsqlite.model.UserModel;

public interface OnUserActionListener {
    void onUpdate(UserModel user);
    void onDelete(UserModel user);
}
