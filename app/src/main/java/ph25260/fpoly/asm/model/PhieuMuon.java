package ph25260.fpoly.asm.model;

import java.util.Date;

    public class PhieuMuon {
        private int id;
        private int idSach;
        private String username;
        private String tensach;
        private int giaSach;
        private int loaiSachId;
        private String ngayThue;
        private int trangThai;

        public PhieuMuon() {
        }

        public PhieuMuon(int id, int idSach, String username, String tensach, int giaSach, int loaiSachId, String ngayThue, int trangThai) {
            this.id = id;
            this.idSach = idSach;
            this.username = username;
            this.tensach = tensach;
            this.giaSach = giaSach;
            this.loaiSachId = loaiSachId;
            this.ngayThue = ngayThue;
            this.trangThai = trangThai;
        }

        public PhieuMuon(String nguoiMuon, int masach, String tensach, String loaisach, int giaThue, String ngaymuon, int i) {
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getIdSach() {
            return idSach;
        }

        public void setIdSach(int idSach) {
            this.idSach = idSach;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getTensach() {
            return tensach;
        }

        public void setTensach(String tensach) {
            this.tensach = tensach;
        }

        public int getGiaSach() {
            return giaSach;
        }

        public void setGiaSach(int giaSach) {
            this.giaSach = giaSach;
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
