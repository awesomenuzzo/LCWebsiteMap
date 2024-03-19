package com.example.testapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.SearchView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;
import com.example.testapplication.databinding.FragmentFirstBinding;

public class FirstFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Use View Binding to inflate the layout
        binding = FragmentFirstBinding.inflate(inflater, container, false);

        // Configure your SearchView using the binding object if you have a SearchView in your layout
        // configureSearchView(binding.searchView);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Setup for the button
        binding.buttonFirst.setOnClickListener(v ->
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_SecondFragment)
        );

        // Initialize the MapView
        MapView mapView = new MapView(requireContext());
        // Configure your MapView if needed, e.g., setting dimensions, enabling features, etc.

        // Add MapView to the FrameLayout
        FrameLayout mapContainer = binding.mapContainer;
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        mapView.setLayoutParams(layoutParams);
        mapContainer.addView(mapView);

        // If you need to interact with mapView after adding it, do so here.
        // For example, mapView.setSomeProperty(value);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Show FloatingActionButton when leaving the fragment
        // Note: Again, ensure your MainActivity exposes the FAB or a method to manipulate it
        // Nullify the binding object
        binding = null;
    }

    // Assuming you have a SearchView with ID `searchView` in your Fragment's layout
    private void configureSearchView(SearchView searchView) {
        // Expand the SearchView to be always open
        searchView.setIconifiedByDefault(false);
        searchView.requestFocus();

        // Set up listeners for search query text changes, submission, etc.
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Handle the search query when submitted
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Handle text changes, e.g., show suggestions
                return true;
            }
        });
    }
}
