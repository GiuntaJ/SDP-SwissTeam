package ch.epfl.swissteam.services;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

/**
 * This class is an activity created to make the user authenticate with Google.
 * This activity sends then the user either to set up a new profile if it is the
 * first time they sign in, either to the main board.
 *
 * @author Julie Giunta
 */
public class SignInActivity extends AppCompatActivity implements View.OnClickListener {
    //Request code for startActivityForResult
    private static final int RC_SIGN_IN = 42;

    public GoogleSignInClient mGoogleSignInClient_;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient_ = GoogleSignIn.getClient(this, gso);

        //Listen to clicks on the signIn button
        findViewById(R.id.button_signin_googlesignin).setOnClickListener(this);
    }

    @Override
    protected void onStart(){
        super.onStart();
        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if(account != null ){
            // Launch main

            Intent mainIntent = new Intent(this, MainActivity.class);
            mainIntent.putExtra(getResources().getString(R.string.all_googleaccounttag) , account);
            startActivity(mainIntent);
        }
    }

    //Starting the intent prompts the user to select a Google account to sign in with
    @Override
    public void onClick(View v) {
        Intent signInIntent = mGoogleSignInClient_.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    /**
     * Sends the new user with the account he signed with in the new profile set up page
     * after the completion of the sign in task of Google.
     * If the task fails, provide a log of the error and then recreate the activity.
     *
     * @param completedTask the task of signing in, completed
     */
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            // Signed in successfully, show authenticated UI
            //TODO Launch newProfileDetails
            Intent newProfileIntent = new Intent(this, MainActivity.class);
            newProfileIntent.putExtra(getResources().getString(R.string.all_googleaccounttag) , account);
            startActivity(newProfileIntent);

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(getResources().getString(R.string.signin_errortag), getResources().getString(R.string.signin_errormsg) + e.getStatusCode());
            recreate();
        }
    }

    /**
     * Getter of the GoogleSignInClient attribute used to sign in the user.
     *
     * @return the GoogleSignInClient used for sign in
     */
    public GoogleSignInClient getmGoogleSignInClient_() {
        return mGoogleSignInClient_;
    }
}