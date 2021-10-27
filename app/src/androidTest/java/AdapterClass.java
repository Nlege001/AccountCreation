
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.accounttcreation.DataClass;
import com.example.accounttcreation.R;

import org.junit.Test;

import java.text.CollationElementIterator;
import java.util.ArrayList;

import javax.annotation.Nonnull;

public class AdapterClass extends RecyclerView.Adapter<AdapterClass.ViewHolder> {

    ArrayList<DataClass> list;
    public AdapterClass (ArrayList<DataClass> list)
    {
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items_db_recycler,viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        viewHolder.email.setText(list.get(i).getEmail());
        viewHolder.faculty.setText(list.get(i).getFaculty());
        viewHolder.name.setText(list.get(i).getName());
        viewHolder.title.setText(list.get(i).getTitle());

    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView email, faculty, name, title;
        public ViewHolder(@Nonnull View ListView){

            super(ListView);
            email = ListView.findViewById(R.id.EmailDB);
            faculty = ListView.findViewById(R.id.FacultyDB);
            name = ListView.findViewById(R.id.NameDB);
            title = ListView.findViewById(R.id.TitleDB);
        }

    }
}
