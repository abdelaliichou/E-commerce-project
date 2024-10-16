package com.example.e_commerce.Modele;

public class GoogelAuthentification {
//    FirebaseAuth mAuth;
//    private GoogleSignInClient mGoogleSignInClient;
//
//    //mAuth = FirebaseAuth.getInstance();
//
//
//    public static void CreatGoogleSignInRequest() {
//        // Configure Google Sign In
//        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(com.firebase.ui.auth.R.string.default_web_client_id))
//                .requestEmail()
//                .build();
//        // Build a google client
//        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
//    }
//
//    public static void SingIn() {
//        Intent intent = mGoogleSignInClient.getSignInIntent();
//        startActivityForResult(intent, RC_SIGN_IN);
//    }
//
//    // this function is handling the Google account that the user will select after he clicks in the accounts in the intent that will appear
//    @Override
//    public static void onActivityResult(int requestCode, int resultCode, Intent data) {
//        // data is the user name , user password ... of  that selected account
//        super.onActivityResult(requestCode, resultCode, data);
//        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent (...);
//        if (requestCode == RC_SIGN_IN) {
//            // the line below is the same as ==> id task.Issuccessful then trey ....
//            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
//            try {
//                // Google Sign In was successful, authenticate with Firebase
//                GoogleSignInAccount account = task.getResult(ApiException.class);
//                // this account is the one that the user selected , and he will bw past as a parameter in the function below that we created
//                firebaseAuthWithGoogle(account);
//            } catch (ApiException e) {
//                // Google Sign In failed, update UI appropriately
//                // ...
//                Toast.makeText(Login_activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                progressBar.setVisibility(View.GONE);
//            }
//        }
//    }
//
//    public static void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
//        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
//        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//            @Override
//            public void onComplete(@NonNull Task<AuthResult> task) {
//                if (task.isSuccessful()) {
//                    // Sign in success, update UI with the signed-in user's information
//                    FirebaseUser user = mAuth.getCurrentUser();
//                    startActivity(new Intent(Login_activity.this, MainActivity.class));
//                    //progressBar.setVisibility(View.GONE);
//                } else {
//                    // If sign in fails, display a message to the user.
//                    Toast.makeText(Login_activity.this, "Authentication Failed !", Toast.LENGTH_SHORT).show();
//                    //progressBar.setVisibility(View.GONE);
//                }
//            }
//        });
//    }
}
