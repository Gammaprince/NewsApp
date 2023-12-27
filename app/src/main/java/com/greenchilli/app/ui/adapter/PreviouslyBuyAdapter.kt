import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greenchilli.app.R
import com.greenchilli.app.databinding.FamousFoodLayoutBinding
import com.greenchilli.app.databinding.PreviouslyBuyBinding
import com.greenchilli.app.model.FamousFoodResponse
import com.greenchilli.app.model.PreviousBuyResponse

class PreviouslyBuyAdapter(
    private val context: Context,
    private val response: List<PreviousBuyResponse>
) :
    RecyclerView.Adapter<PreviouslyBuyAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.previously_buy, parent, false)
        return ViewHolder(
            PreviouslyBuyBinding.inflate(
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

    inner class ViewHolder(val binding: PreviouslyBuyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val foodName: TextView = itemView.findViewById<TextView>(R.id.foodName)
        private val foodImage: ImageView = itemView.findViewById<ImageView>(R.id.foodImage)
        private val foodPrice = itemView.findViewById<TextView>(R.id.foodPrice)
        private val addToCart = itemView.findViewById<TextView>(R.id.addToCart)

        fun bind(
            foodName: String,
            foodImage: Int,
            foodPrice: String,
            addToCart: String = "Buy Again"
        ) {
            binding.let {
                it.foodName.text = foodName
                it.foodImage.setImageResource(foodImage)
                it.foodPrice.text = foodPrice
                it.buyAgain.text = addToCart
            }
        }
    }
}
