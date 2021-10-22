
public class View {
private String Email;
private String Faculty;
private String Name;
private String Title;

    public View() {
    }

    public View(String email, String faculty, String name, String title) {
        Email = email;
        Faculty = faculty;
        Name = name;
        Title = title;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getFaculty() {
        return Faculty;
    }

    public void setFaculty(String faculty) {
        Faculty = faculty;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
}
