import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greenchilli.app.R
import com.greenchilli.app.databinding.FamousFoodLayoutBinding
import com.greenchilli.app.domain.Resource
import com.greenchilli.app.model.FamousFoodResponse
import com.greenchilli.app.model.SearchViewResponse
import com.greenchilli.app.ui.viewmodel.SearchViewModel

class SearchViewAdapter(
    private val context: Context,
    private val response: List<FamousFoodResponse>,
    private val viewModel: SearchViewModel
) :
    RecyclerView.Adapter<SearchViewAdapter.ViewHolder>() {

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

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = response[position]
        holder.addToCart.setOnClickListener {
            viewModel.addToCart(data.image,data.foodName,data.price)
            viewModel.isAddedToCart.observe(context as FragmentActivity, Observer {
                when(it){
                    is Resource.Success -> {
                        Toast.makeText(context,"Added to Cart", Toast.LENGTH_SHORT).show()
                    }
                    is Resource.Error -> {
                        Toast.makeText(context,"Something went wrong", Toast.LENGTH_SHORT).show()
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
        val foodName: TextView = itemView.findViewById<TextView>(R.id.foodName)
        val foodImage: ImageView = itemView.findViewById<ImageView>(R.id.foodImage)
        val foodPrice = itemView.findViewById<TextView>(R.id.foodPrice)
        val addToCart = itemView.findViewById<TextView>(R.id.addToCart)

        fun bind(
            foodName: String,
            foodImage: String,
            foodPrice: String,
            addToCart: String = "Add to cart"
        ) {
            binding.let {
                it.foodName.text = foodName
                Glide.with(context).load(foodImage).into(binding.foodImage)
                it.foodPrice.text = foodPrice
                it.addToCart.text = addToCart
            }
        }
    }
}
