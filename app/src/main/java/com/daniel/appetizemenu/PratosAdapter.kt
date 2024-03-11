import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daniel.appetizemenu.FragmentEntradas
import com.daniel.appetizemenu.OnCheckedChangeListener
import com.daniel.appetizemenu.Prato
import com.daniel.appetizemenu.R
import com.daniel.appetizemenu.RestaurantMenu


class PratosAdapter(
    private val pratos: List<Prato>,
    private val listener: OnCheckedChangeListener
) : RecyclerView.Adapter<PratosAdapter.ViewHolder>() {

    private var total: Double = 0.0

    fun calcularTotal(): Double {
        return pratos.filter { it.isChecked }.sumByDouble { it.valor.toDouble() }
    }

    fun getItensSelecionados(): List<Prato> {
        return pratos.filter { it.isChecked }
    }


    fun clearSelections() {
        pratos.forEach { it.isChecked = false }
        notifyDataSetChanged()
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkBox: CheckBox = itemView.findViewById(R.id.checkBoxEntradasPrato)
        val valorTextView: TextView = itemView.findViewById(R.id.valorTextView)
        val tempoPreparoTextView: TextView = itemView.findViewById(R.id.tempoPreparoTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_prato, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val prato = pratos[position]


        // Configurar os elementos do layout com os dados do prato
        holder.checkBox.isChecked = prato.isChecked
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            prato.isChecked = isChecked
            listener.onItemSelected(prato, isChecked)
            total = calcularTotal()

        }
        holder.checkBox.text = prato.nome
        holder.valorTextView.text = "R$ ${prato.valor}"
        holder.tempoPreparoTextView.text = "Tempo de Preparo: ${prato.tempoPreparo}"
    }

    override fun getItemCount(): Int {
        return pratos.size
    }
}
