package assignment.csc214.wizard;


import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import assignment.csc214.wizard.databinding.FragmentWizardBinding;

/**
 * A simple {@link Fragment} subclass.
 */
public class wizardFragment extends Fragment {

    public static final String DAY = "day";
    public static final String PROMPT = "prompt";
    public static final String BUNDLE = "bundle";

    public static final String PROMPT01 = "Tell the most uncreative speech you can imagine";
    public static final String PROMPT02 = "Write at least 5 analogies";
    public static final String PROMPT03 = "Tell a speech that starts with a joke and ends with a reference to it";
    public static final String PROMPT04 = "Incorporate sadness into your speech";
    public static final String PROMPT05 = "Incorporate a personal story";

    private String day;
    private String prompt;
    private Bundle bundle;

    private FragmentWizardBinding mWizardBinding;

    public static wizardFragment newInstance(String day, String prompt, Bundle bundle){
        wizardFragment fragment = new wizardFragment();
        Bundle bunBunBundle = new Bundle();
        bunBunBundle.putString(DAY, day);
        bunBunBundle.putString(PROMPT, prompt);
        bunBunBundle.putBundle(BUNDLE, bundle);
        fragment.setArguments(bunBunBundle);
        return fragment;
    }

    public wizardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        day = getArguments().getString(DAY);
        prompt = getArguments().getString(PROMPT);
        bundle = new Bundle();
        bundle = getArguments().getBundle(BUNDLE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mWizardBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_wizard, container, false);

        View viewRoot = mWizardBinding.getRoot();
        getActivity().setTitle(""+day+" -- ");
        mWizardBinding.wizardPrompt.setText(prompt);
        if (bundle!= null)
            mWizardBinding.wizardImage.setImageBitmap((Bitmap) bundle.getParcelable("map"));
        else
            Toast.makeText(getContext(), "was null", Toast.LENGTH_SHORT).show();
        return viewRoot;
    }

}
