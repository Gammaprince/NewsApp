import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.greenchilli.app.R
import com.greenchilli.app.databinding.NotPurchasedLayoutBinding
import com.greenchilli.app.databinding.PreviouslyBuyBinding
import com.greenchilli.app.model.CartFragmentResponse

class PreviouslyBuyAdapter(
    private val context: Context,
    private val response: List<CartFragmentResponse>?
) :
    RecyclerView.Adapter<PreviouslyBuyAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            return ViewHolder(
                PreviouslyBuyBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val count = response!!.size - 2 - position
        val data = response?.get(count)
        if(count>0){
            holder.bind(data?.foodName ?: "", data?.foodImage ?: "", data?.price ?: "")
        }
    }

    override fun getItemCount(): Int {
        val count = response?.size ?: 0
        if(count == 0) return 0;
        else return count-1
    }

    inner class ViewHolder(val binding: PreviouslyBuyBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val foodName: TextView = itemView.findViewById<TextView>(R.id.foodName)
        private val foodImage: ImageView = itemView.findViewById<ImageView>(R.id.foodImage)
        private val foodPrice = itemView.findViewById<TextView>(R.id.foodPrice)
        private val addToCart = itemView.findViewById<TextView>(R.id.addToCart)

        fun bind(
            foodName: String,
            foodImage: String,
            foodPrice: String,
            addToCart: String = "Buy Again"
        ) {
            binding.let {
                it.foodName.text = foodName
                Glide.with(context).load(foodImage).into(binding.foodImage)
                it.foodPrice.text = foodPrice
                it.buyAgain.text = addToCart
            }
        }
    }
    inner class ViewHolder2(val binding : NotPurchasedLayoutBinding) : RecyclerView.ViewHolder(binding.root){

    }
}
