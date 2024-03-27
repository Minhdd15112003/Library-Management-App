package ph25260.fpoly.asm.model;

public class Sach {
    private int id;
    private String tensach;
    private String giaSach;
    private int loaiSachId;

    public Sach(int id, String tensach, String giaSach, int loaiSachId) {
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

    public String getGiaSach() {
        return giaSach;
    }

    public void setGiaSach(String giaSach) {
        this.giaSach = giaSach;
    }

    public int getLoaiSachId() {
        return loaiSachId;
    }

    public void setLoaiSachId(int loaiSachId) {
        this.loaiSachId = loaiSachId;
    }
}
