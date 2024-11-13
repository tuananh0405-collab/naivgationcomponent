package com.example.navigationcomponent.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.navigationcomponent.R;
import com.example.navigationcomponent.databinding.ItemProductBinding;
import com.example.navigationcomponent.model.Product;
import com.example.navigationcomponent.viewmodel.ProductViewModel;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{
    List<Product> products;
    ProductViewModel viewModel;

    public ProductAdapter(List<Product> products, ProductViewModel viewModel) {
        this.products = products;
        this.viewModel = viewModel;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemProductBinding binding = ItemProductBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ProductViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(products.get(position));
        holder.binding.setViewModel(viewModel);
        holder.binding.getRoot().setOnClickListener(v -> {
            viewModel.setSelectedProduct(products.get(position));
            viewModel.setImageResId(products.get(position).getImageResId());
            Navigation.findNavController(v).navigate(R.id.action_listFragment_to_detailFragment);
        });
        holder.binding.getRoot().setOnLongClickListener(v -> {
            viewModel.onProductLongClicked(products.get(position), v.getContext());
            return true;
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder{

        ItemProductBinding binding;
        public ProductViewHolder(@NonNull ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(Product product){
            binding.setProduct(product);
            binding.executePendingBindings();
        }
    }
}
