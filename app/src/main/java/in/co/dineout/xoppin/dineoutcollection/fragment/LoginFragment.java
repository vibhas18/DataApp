package in.co.dineout.xoppin.dineoutcollection.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.dineout.android.volley.Request;
import com.dineout.android.volley.Response;
import com.dineout.android.volley.VolleyError;
import com.example.datanetworkmodule.DataPreferences;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import in.co.dineout.xoppin.dineoutcollection.R;
import in.co.dineout.xoppin.dineoutcollection.utils.Utils;

/**
 * Created by prateek.aggarwal on 5/6/16.
 */
public class LoginFragment extends MasterDataFragment implements View.OnClickListener,Response.Listener<String>,Response.ErrorListener {

    private EditText mUsername;
    private EditText mPassword;
    private final int LOGIN_REQUEST_IDENTIFIER = 101;


    public static LoginFragment newInstance(){
        LoginFragment fragment = new LoginFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        initializeView(getView());
    }

    private void initializeView(View v){
        mUsername = (EditText) v.findViewById(R.id.username);
        mPassword =  (EditText) v.findViewById(R.id.password);
        v.findViewById(R.id.submit).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        String email = mUsername.getText().toString().trim();
        String password = mPassword.getText().toString().trim();
        if(validateDetail(email,password)){

          loginUser(email,password);
        }
    }

    private boolean validateDetail(String email,String password){



        if (TextUtils.isEmpty(email)) {
            Toast.makeText(getActivity(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(getActivity(), "Password cannot be empty", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void loginUser(String username,String password){

        Map<String,String> param = new HashMap<>();
        param.put("email",username);
        param.put("password",password);

        showLoadingDialog(false);
        getNetworkManager().stringRequestPost(LOGIN_REQUEST_IDENTIFIER,"login",param,this,this,false);
    }

    @Override
    public void onErrorResponse(Request request, VolleyError error) {
        hideLoadingDialog();
    }

    @Override
    public void onResponse(Request<String> request, String responseObject, Response<String> response) {

        hideLoadingDialog();

        try{
        JSONObject resp = new JSONObject(responseObject);
        if (null != response && Utils.getStringVal(resp, "message").equalsIgnoreCase("Login successful")) {
            DataPreferences.saveUser(getActivity(), Utils.getJsonObject(resp, "data"));
            Toast.makeText(getActivity(), Utils.getStringVal(resp, "message"), Toast.LENGTH_SHORT).show();

           popBackStack(getActivity().getSupportFragmentManager());
            DashboardFragment fragment = DashboardFragment.newInstance();
            addToBackStack(getActivity(),fragment);
        }

        Toast.makeText(getActivity(), Utils.getStringVal(resp, "message"), Toast.LENGTH_SHORT).show();
        }catch (Exception e){

        }
    }
}
