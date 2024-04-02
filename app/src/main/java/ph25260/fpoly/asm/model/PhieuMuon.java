package ph25260.fpoly.asm.model;

import java.util.Date;

    public class PhieuMuon {
        private int id;
        private int idUser;
        private int idSach;
        private int loaiSachId;
        private String ngayThue;
        private int trangThai;

        public PhieuMuon() {
        }


        public PhieuMuon(int id, int idUser, int idSach, int loaiSachId, String ngayThue, int trangThai) {
            this.id = id;
            this.idUser = idUser;
            this.idSach = idSach;
            this.loaiSachId = loaiSachId;
            this.ngayThue = ngayThue;
            this.trangThai = trangThai;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIdUser() {
            return idUser;
        }

        public void setIdUser(int idUser) {
            this.idUser = idUser;
        }

        public int getIdSach() {
            return idSach;
        }

        public void setIdSach(int idSach) {
            this.idSach = idSach;
        }

        public int getLoaiSachId() {
            return loaiSachId;
        }

        public void setLoaiSachId(int loaiSachId) {
            this.loaiSachId = loaiSachId;
        }

        public String getNgayThue() {
            return ngayThue;
        }

        public void setNgayThue(String ngayThue) {
            this.ngayThue = ngayThue;
        }

        public int getTrangThai() {
            return trangThai;
        }

        public void setTrangThai(int trangThai) {
            this.trangThai = trangThai;
        }
    }
