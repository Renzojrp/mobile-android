package pe.com.mucontact.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.androidnetworking.widget.ANImageView;

import java.util.List;

import pe.com.mucontact.R;
import pe.com.mucontact.models.Contract;

/**
 * Created by romer on 4/8/2017.
 */

public class ContractsAdapter extends RecyclerView.Adapter<ContractsAdapter.ViewHolder>{
    private List<Contract> contracts;

    public ContractsAdapter(List<Contract> contracts){this.contracts = contracts;}

    public ContractsAdapter(){}

    @Override
    public ContractsAdapter.ViewHolder onCreateViewHolder(
            ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater
                .from(parent.getContext())
                .inflate(R.layout.content_contract, parent,false));
    }

    @Override
    public void onBindViewHolder(
            ContractsAdapter.ViewHolder holder, int position) {
        holder.craftmanTextView.setText(contracts.get(position).getCraftman().getUser().getDisplayName());
        holder.instrumentTextView.setText(contracts.get(position).getPublication().getInstrument());
        holder.photoPublicationANImageView.setErrorImageResId(R.mipmap.ic_launcher);
        holder.photoPublicationANImageView.setDefaultImageResId(R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount()  {
        return contracts.size();
    }

    public List<Contract> getContracts() {
        return contracts;
    }

    public ContractsAdapter setContracts(List<Contract> contracts) {
        this.contracts = contracts;
        return this;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView craftmanTextView;
        TextView instrumentTextView;
        ANImageView photoPublicationANImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            craftmanTextView = (TextView) itemView.findViewById(R.id.craftmanTextView);
            instrumentTextView = (TextView) itemView.findViewById(R.id.instrumentTextView);
            photoPublicationANImageView = (ANImageView) itemView.findViewById(R.id.photoPublicationANImageView);
        }
    }
}
