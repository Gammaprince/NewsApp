import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.greenchilli.app.R
import com.greenchilli.app.databinding.NotificationItemBinding
import com.greenchilli.app.model.NotificationResponse

class NotificationAdapter(
    private val context: Context,
    private val response: List<NotificationResponse>
) :
    RecyclerView.Adapter<NotificationAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            NotificationItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = response[position]
        holder.bind(data.icon,data.message)
    }

    override fun getItemCount(): Int {
        return response.size
    }

    inner class ViewHolder(val binding: NotificationItemBinding) :
        RecyclerView.ViewHolder(binding.root) {


        fun bind(
            icon : Int , message : String
        ) {
            binding.let {
                it.icon.setImageResource(icon)
                it.message.text = message
            }
        }
    }
}
