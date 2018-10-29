package jcsandoval.sandroidresume.com.sandroidresume.Fragments;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.transitionseverywhere.ChangeBounds;
import com.transitionseverywhere.Transition;
import com.transitionseverywhere.TransitionManager;
import com.transitionseverywhere.TransitionSet;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindViews;
import butterknife.ButterKnife;
import jcsandoval.sandroidresume.com.sandroidresume.Activities.HomeActivity;
import jcsandoval.sandroidresume.com.sandroidresume.Adapter.TextWatcherAdapter;
import jcsandoval.sandroidresume.com.sandroidresume.R;
import jcsandoval.sandroidresume.com.sandroidresume.Utils.Rotate;
import jcsandoval.sandroidresume.com.sandroidresume.Utils.TextSizeTransition;

import static jcsandoval.sandroidresume.com.sandroidresume.Utils.Constants.EMAIL_EXPRESSION;

public class LogInFragment extends AuthFragment {

    public Button login_button;
    public ProgressBar progressBarLogin;
    public LinearLayout progressBardLinearLogin;
    @BindViews(value = {R.id.email_input_edit, R.id.password_input_edit})
    protected List<TextInputEditText> views;
    private FirebaseAuth auth;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        login_button = (Button) view.findViewById(R.id.login_button);
        progressBarLogin = (ProgressBar) view.findViewById(R.id.progressBarLogin);
        progressBardLinearLogin = (LinearLayout) view.findViewById(R.id.progressLayoutLogin);
        auth = FirebaseAuth.getInstance();
        caption.setText(getString(R.string.log_in_label));
        for (TextInputEditText editText : views) {
            if (editText.getId() == R.id.password_input_edit) {
                final TextInputLayout inputLayout = ButterKnife.findById(view, R.id.password_input);
                Typeface boldTypeface = Typeface.defaultFromStyle(Typeface.BOLD);
                inputLayout.setTypeface(boldTypeface);
                editText.addTextChangedListener(new TextWatcherAdapter() {
                    @Override
                    public void afterTextChanged(Editable editable) {
                        inputLayout.setPasswordVisibilityToggleEnabled(editable.length() > 6);
                    }
                });
            }

            editText.setOnFocusChangeListener((temp, hasFocus) -> {
                if (!hasFocus) {
                    boolean isEnabled = Objects.requireNonNull(editText.getText()).length() > 0;
                    editText.setSelected(isEnabled);
                }
            });
        }
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validateEmail() && validatePassword()) {
                    progressBardLinearLogin.setVisibility(View.VISIBLE);
                    progressBarLogin.setVisibility(View.VISIBLE);
                    final TextInputEditText editTextEmail = ButterKnife.findById(view, R.id.email_input_edit);
                    final TextInputEditText editTextPass = ButterKnife.findById(view, R.id.password_input_edit);
                    String email = Objects.requireNonNull(editTextEmail.getText()).toString().trim();
                    String pass = Objects.requireNonNull(editTextPass.getText()).toString().trim();
                    auth.signInWithEmailAndPassword(email, pass)
                            .addOnCompleteListener(Objects.requireNonNull(getActivity()), new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    Toast.makeText(getContext(), "loginWithEmail:onComplete:" + task.isSuccessful(), Toast.LENGTH_SHORT).show();
                                    if (!task.isSuccessful()) {
                                        progressBarLogin.setVisibility(View.INVISIBLE);
                                        progressBardLinearLogin.setVisibility(View.INVISIBLE);
                                        Toast.makeText(getContext(), "Authentication failed." + task.getException(),
                                                Toast.LENGTH_SHORT).show();
                                    } else {
                                        progressBarLogin.setVisibility(View.INVISIBLE);
                                        progressBardLinearLogin.setVisibility(View.INVISIBLE);
                                        Intent intent = new Intent(getActivity(), HomeActivity.class);
                                        startActivity(intent);
                                        Toast.makeText(getContext(), "Authentication success." + task.getResult(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });
    }

    public boolean validatePassword() {
        for (TextInputEditText editText : views) {
            if (editText.getId() == R.id.password_input_edit) {
                if (editText.length() < 6) {
                    Toast.makeText(getContext(), "Password must have at least, 6 characters!", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        }
        return true;
    }

    public boolean validateEmail() {
        for (TextInputEditText editText : views)
            if (editText.getId() == R.id.email_input_edit) {
                if (!isEmailValid(Objects.requireNonNull(editText.getText()).toString())) {
                    Toast.makeText(getContext(), "This email is not valid, please type a valid one!", Toast.LENGTH_LONG).show();
                    return false;
                }
            }
        return true;
    }

    public static boolean isEmailValid(String email) {
        boolean isValid = false;

        EMAIL_EXPRESSION = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(EMAIL_EXPRESSION, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }

    @Override
    public int authLayout() {
        return R.layout.login_fragment;
    }

    @Override
    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public void fold() {
        lock = false;
        Rotate transition = new Rotate();
        transition.setEndAngle(-90f);
        transition.addTarget(caption);
        TransitionSet set = new TransitionSet();
        set.setDuration(getResources().getInteger(R.integer.duration));
        ChangeBounds changeBounds = new ChangeBounds();
        set.addTransition(changeBounds);
        set.addTransition(transition);
        TextSizeTransition sizeTransition = new TextSizeTransition();
        sizeTransition.addTarget(caption);
        set.addTransition(sizeTransition);
        set.setOrdering(TransitionSet.ORDERING_TOGETHER);
        final float padding = getResources().getDimension(R.dimen.folded_label_padding) / 2;
        set.addListener(new Transition.TransitionListenerAdapter() {
            @Override
            public void onTransitionEnd(Transition transition) {
                super.onTransitionEnd(transition);
                caption.setTranslationX(-padding);
                caption.setRotation(0);
                caption.setVerticalText(true);
                caption.requestLayout();

            }
        });
        TransitionManager.beginDelayedTransition(parent, set);
        caption.setTextSize(TypedValue.COMPLEX_UNIT_PX, caption.getTextSize() / 2);
        caption.setTextColor(Color.WHITE);
        ConstraintLayout.LayoutParams params = getParams();
        params.leftToLeft = ConstraintLayout.LayoutParams.UNSET;
        params.verticalBias = 0.5f;
        caption.setLayoutParams(params);
        caption.setTranslationX(caption.getWidth() / 8 - padding);
    }

    @Override
    public void clearFocus() {
        for (View view : views) view.clearFocus();
    }

}