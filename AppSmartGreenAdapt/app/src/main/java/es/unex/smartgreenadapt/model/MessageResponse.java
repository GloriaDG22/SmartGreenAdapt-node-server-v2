package es.unex.smartgreenadapt.model;

import com.google.gson.annotations.SerializedName;

public class MessageResponse {


        @SerializedName("message")
        private MessageInfo message;


        public int getAffectedRows() {
                return message.affectedRows;
        }

        public int getInsertId() {
                return message.insertId;
        }


        public MessageResponse(int affectedRows, int insertId) {
                this.message.affectedRows = affectedRows;
                this.message.insertId = insertId;
        }

        private class MessageInfo {

                @SerializedName("affectedRows")
                public int affectedRows;
                @SerializedName("insertId")
                public int insertId;
        }
}
