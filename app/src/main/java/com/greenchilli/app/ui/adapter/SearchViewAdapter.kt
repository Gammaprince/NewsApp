import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greenchilli.app.R
import com.greenchilli.app.databinding.FamousFoodLayoutBinding
import com.greenchilli.app.model.FamousFoodResponse
import com.greenchilli.app.model.SearchViewResponse

class SearchViewAdapter(
    private val context: Context,
    private val response: List<SearchViewResponse>
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
        holder.bind(data.foodName, data.image, data.price)
    }

    override fun getItemCount(): Int {
        return response.size
    }

    inner class ViewHolder(val binding: FamousFoodLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val foodName: TextView = itemView.findViewById<TextView>(R.id.foodName)
        private val foodImage: ImageView = itemView.findViewById<ImageView>(R.id.foodImage)
        private val foodPrice = itemView.findViewById<TextView>(R.id.foodPrice)
        private val addToCart = itemView.findViewById<TextView>(R.id.addToCart)

        fun bind(
            foodName: String,
            foodImage: Int,
            foodPrice: String,
            addToCart: String = "Add to cart"
        ) {
            binding.let {
                it.foodName.text = foodName
                it.foodImage.setImageResource(foodImage)
                it.foodPrice.text = foodPrice
                it.addToCart.text = addToCart
            }
        }
    }
}
