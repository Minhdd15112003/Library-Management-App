package ph25260.fpoly.asm.model;

public class Sach {
    private int id;
    private String tensach;
    private int giaSach;
    private int loaiSachId;

    public Sach(int id, String tensach, int giaSach, int loaiSachId) {
        this.id = id;
        this.tensach = tensach;
        this.giaSach = giaSach;
        this.loaiSachId = loaiSachId;
    }

    public Sach() {
    }

    public Sach(String tensach, String giathue, int loaisach) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
}
