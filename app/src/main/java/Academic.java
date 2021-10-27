

public class Academic {
private String Email;
private String Faculty;
private String Name;
private String Title;

    public Academic() {
    }

    public Academic(String email, String faculty, String name, String title) {
        this.Email = email;
        this.Faculty = faculty;
        this.Name = name;
        this.Title = title;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        this.Faculty = faculty;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }
}
