package pe.com.mucontact.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import pe.com.mucontact.MuContactApp;
import pe.com.mucontact.R;
import pe.com.mucontact.adapters.ContractsAdapter;
import pe.com.mucontact.models.Contract;
import pe.com.mucontact.models.Craftman;
import pe.com.mucontact.models.Publication;
import pe.com.mucontact.models.User;
import pe.com.mucontact.network.NewApiService;


public class ContractFragment extends Fragment {
    private RecyclerView contractsRecyclerView;
    private ContractsAdapter contractsAdapter;
    private RecyclerView.LayoutManager contractsLayoutManager;
    private List<Contract> contracts;
    private static String TAG = "MuContact";
    private User user;
    private User currentUser;
    private Craftman craftman;
    private Publication publication;

    public ContractFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_contract, container, false);
        contractsRecyclerView = (RecyclerView) view.findViewById(R.id.contractRecyclerView);
        contracts = new ArrayList<>();
        contractsAdapter = (new ContractsAdapter()).setContracts(contracts);
        contractsLayoutManager = new LinearLayoutManager(view.getContext());
        contractsRecyclerView.setAdapter(contractsAdapter);
        contractsRecyclerView.setLayoutManager(contractsLayoutManager);
        currentUser = MuContactApp.getInstance().getCurrentUser();
        updateContracts();
        return view;
    }

    private void updateContracts() {
        AndroidNetworking
                .get(NewApiService.CONTRACT_USER_URL)
                .addPathParameter("user_id", currentUser.get_id())
                .setTag(TAG)
                .setPriority(Priority.LOW)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        if(response == null) return;
                        try {
                            contracts = Contract.build(response.getJSONArray("contracts"), user, publication, craftman);
                            Log.d(TAG, "Found Publications: " + String.valueOf(contracts.size()));
                            contractsAdapter.setContracts(contracts);
                            contractsAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                        Log.d(TAG, anError.getMessage());
                    }
                });
    }

}
