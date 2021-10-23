package com.softeng306.p2.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.softeng306.p2.Database.CoreActivity;
import com.softeng306.p2.Database.IVehicleDataAccess;
import com.softeng306.p2.Database.VehicleService;
import com.softeng306.p2.DetailsActivity;
import com.softeng306.p2.Listeners.OnGetVehicleListener;
import com.softeng306.p2.ViewModel.VehicleModel;
import com.softeng306.p2.DataModel.Vehicle;
import com.softeng306.p2.R;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TopAdapter extends RecyclerView.Adapter<TopAdapter.ViewHolder> implements CoreActivity {
    //Define fields for later use in methods
    ArrayList<VehicleModel> vModels;
    Context context;
    IVehicleDataAccess vda;


    public TopAdapter(Context context,ArrayList<VehicleModel> topModels){
        this.context = context;
        this.vModels = topModels;
    }

    /**
     * Method creates a new view holder and initialises some private fields to be used by RecyclerView.
     * @param parent the ViewGroup which the new View will be added after it is bound to an adapter position.
     * @param viewType type of the view passed in
     * @return a new ViewHolder that holds a view inflated with vehicle_item(s)
     */
    @NonNull
    @Override
    public TopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //create view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.top_item,parent,false);
        return new ViewHolder(view);
    }

    /**
     * Method that calls the database object and retrieve information needed for displaying the vehicle
     * @param holder
     * @param position
     */
    @Override
    public void onBindViewHolder(@NonNull TopAdapter.ViewHolder holder, int position) {
        VehicleService.getInstance().InjectService(this);
        String vehicleName = vModels.get(position).getVName();

        //get the information from database for each vehicle title supplied
        vda.getVehicleByName(vehicleName,new OnGetVehicleListener() {

            @Override
            public void onCallBack(List<Vehicle> vehicleList) {
                for (Vehicle v: vehicleList){
                    String fileName = convertNameToFileName(vehicleName)+"_"+1;
                    holder.imageView.setImageResource(context.getResources().getIdentifier(fileName, "drawable", context.getPackageName()));
                    holder.titleView.setText(vehicleName);

                    // Convert price to display as the conventional format for pricing with commas and 2dp
                    String priceStr = Float.toString(v.getPrice());
                    double amount = Double.parseDouble(priceStr);
                    DecimalFormat formatter = new DecimalFormat("#,###.00");

                    holder.priceView.setText("$"+formatter.format(amount));
                    holder.topLayout.setOnClickListener(new View.OnClickListener(){
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(context, DetailsActivity.class);
                            final CharSequence carTitle = holder.titleView.getText();
                            intent.putExtra("title",String.valueOf(carTitle));
                            context.startActivity(intent);
                        }
                    });
                }

            }
        });

    }

    /**
     * A helper method that convert name of car to image name
     * @param carTitle
     * @return string in the format of image file name
     */
    private String convertNameToFileName(String carTitle){
        return carTitle.toLowerCase(Locale.ROOT).replace(" ","_").replace("-","_");
    }


    @Override
    public int getItemCount() {
        return vModels.size();
    }

    /**
     * initialize the database object
     * @param vehicleDataAccess
     */
    @Override
    public void SetDataAccess(IVehicleDataAccess vehicleDataAccess) {
        vda = vehicleDataAccess;
    }

    /**
     * Method sets up global variables in a view holder and linked to their id
     */
    public class ViewHolder extends RecyclerView.ViewHolder {
        //Initialize variable
        ImageView imageView;
        TextView titleView,priceView;
        LinearLayout topLayout;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            priceView = itemView.findViewById(R.id.top_price_view);
            imageView = itemView.findViewById(R.id.top_image_view);
            titleView = itemView.findViewById(R.id.top_name_view);
            topLayout = itemView.findViewById(R.id.topLayout);
        }
    }


}
