import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greenchilli.app.R
import com.greenchilli.app.data.AddToCartRepository
import com.greenchilli.app.databinding.FamousFoodLayoutBinding
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.FamousFoodResponse
import com.greenchilli.app.ui.fragment.ItemDetail
import com.greenchilli.app.ui.viewmodel.HomeFragmentViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class HomeFragmentAdapter(
    private val context: Context,
    private val response: List<FamousFoodResponse>,
    private val viewModel: HomeFragmentViewModel
) :
    RecyclerView.Adapter<HomeFragmentAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.famous_food_layout, parent, false)
        return ViewHolder(
            FamousFoodLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = response[position]
        holder.layout.setOnClickListener {
            val bundle : Bundle = Bundle()
            bundle.let {
                it.putString("link",data.image)
                it.putString("foodname",data.foodName)
                it.putString("description",data.desc)
                it.putString("ingredient",data.ingr)
            }
            showNewFragment(holder , bundle)
        }
        holder.addToCart.setOnClickListener {

                val addToCart = AddToCartRepository()
                viewModel.addToCart(data.image,data.foodName,data.price)
                viewModel.isAddedToCart.observe(context as FragmentActivity, Observer {
                    when(it){
                        is Resource.Success -> {
                            Toast.makeText(context,"Added to Cart",Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Error -> {
                            Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show()
                        }
                        is Resource.Loading -> {

                        }
                    }
                })




        }
        holder.bind(data.foodName, data.image, data.price)
    }

    override fun getItemCount(): Int {
        return response.size
    }

    inner class ViewHolder(val binding: FamousFoodLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val layout : ConstraintLayout = binding.famousFoodLayout
        val addToCart : TextView = binding.addToCart

        fun bind(
            foodName: String,
            foodImage: String,
            foodPrice: String,
            addToCart: String = "Add to cart"
        ) {
            binding.let {
                it.foodName.text = foodName
                Glide.with(context).load(foodImage).into(it.foodImage);
                it.foodPrice.text = foodPrice
                it.addToCart.text = addToCart
            }
        }
    }
    private fun showNewFragment(holder : ViewHolder , bundle : Bundle) {
        val myFragment = ItemDetail()
        myFragment.arguments = bundle
        val transaction = (holder.itemView.context as FragmentActivity)
            .supportFragmentManager
            .beginTransaction()
        transaction.add(R.id.homeLayout,myFragment)
        transaction.addToBackStack(null) // If needed
        transaction.commit()
    }
}
