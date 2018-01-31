package com.example.admin.skolonetapp.Pojo;

import com.androidbuts.multispinnerfilter.KeyPairBoolData;

/**
 * Created by DELL on 1/18/2018.
 */

public class BoardList extends KeyPairBoolData {
    int boardId;
    String boardName;

    public int getBoardId() {
        return boardId;
    }

    public int getItemId(int position) {
        return position;
    }

    public void setBoardId(int boardId) {
        this.boardId = boardId;
    }

    public String getBoardName() {
        return boardName;
    }

    public void setBoardName(String boardName) {
        this.boardName = boardName;
    }


}
