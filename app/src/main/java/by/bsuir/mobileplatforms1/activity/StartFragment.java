package by.bsuir.mobileplatforms1.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import by.bsuir.mobileplatforms1.R;

public class StartFragment extends Fragment {

    public StartFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        view.findViewById(R.id.button_start_game).setOnClickListener(this::buttonStartGameClickHandler);
        view.findViewById(R.id.button_show_table).setOnClickListener(this::buttonShowTableClickHandler);
    }

    private void buttonStartGameClickHandler(View view) {
        startActivity(new Intent(getContext(), GameActivity.class));
    }

    private void buttonShowTableClickHandler(View view) {
        NavHostFragment.findNavController(this).navigate(R.id.action_startFragment_to_resultTableFragment);
    }
}