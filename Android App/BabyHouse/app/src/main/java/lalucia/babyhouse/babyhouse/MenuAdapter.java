package lalucia.babyhouse.babyhouse;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Duran on 7/23/2016.
 */
public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.viewHolder> {

    private LayoutInflater inflater;
    List<MenuItems> data;
    private Context context;
    private RecycleClickListener clickListener;
    public MenuAdapter(Context con, List<MenuItems> dataList){
        this.context = con;
        inflater = LayoutInflater.from(con);
        this.data = dataList;
    }
    //******************************************************************
    @Override
    public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.menu_row,parent,false);
        viewHolder objHolder = new viewHolder(view);
        return objHolder;
    }
    //******************************************************************
    @Override
    public void onBindViewHolder(viewHolder holder, int position) {
        MenuItems currentMenuItem = data.get(position);
        holder.menuName.setText(currentMenuItem.menuItemName);
    }
    //******************************************************************
    public void setClickListener(RecycleClickListener recycleClickListener){
        clickListener = recycleClickListener;
    }
    @Override
    public int getItemCount() {
        return data.size();
    }
    //******************************************************************
    public class viewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView menuName;
        public viewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            menuName = (TextView) itemView.findViewById(R.id.tvMenuItem);
        }
        //******************************************************************
        @Override
        public void onClick(View v)
        {
            if(clickListener != null)
            {
                clickListener.recycleItemClick(v,getLayoutPosition());
            }
        }
        //******************************************************************
    }

    public interface RecycleClickListener{
        public void recycleItemClick(View v, int pos);
    }
}
