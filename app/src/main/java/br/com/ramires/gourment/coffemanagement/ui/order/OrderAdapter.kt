package br.com.ramires.gourment.coffemanagement.ui.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import br.com.ramires.gourment.coffemanagement.R
import br.com.ramires.gourment.coffemanagement.data.model.Order
import br.com.ramires.gourment.coffemanagement.data.model.OrderStatus
import br.com.ramires.gourment.coffemanagement.databinding.ItemOrderBinding

class OrderAdapter(
    private val onOrderClick: (Int) -> Unit,
    private val onOrderSave: (Order) -> Unit
) : RecyclerView.Adapter<OrderAdapter.OrderViewHolder>() {

    private val orders = mutableListOf<Order>()
    private var expandedOrderId: Int? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderViewHolder {
        val binding = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrderViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrderViewHolder, position: Int) {
        val order = orders[position]
        val isExpanded = order.id == expandedOrderId

        with(holder.binding) {

            // Exibe ou oculta os detalhes do pedido
            layoutOrderDetails.visibility = if (isExpanded) View.VISIBLE else View.GONE

            // Altera a cor do fundo do título ao expandir
            textViewOrderTitle.setBackgroundResource(
                if (isExpanded) R.drawable.item_order_header_background_selected
                else R.drawable.item_order_header_background
            )

            // Titulo do pedido
            textViewOrderTitle.text = "${order.status} Pedido #${order.id}"

            // Formatação dos detalhes do pedido como lista unificada
            textViewOrderDetails.text = order.details?.joinToString(separator = "\n") {
                "${it.quantity}x - ${it.productName}"
            } ?: "Nenhum detalhe de pedido"

            // Valor total
            textViewTotalPrice.text = "Valor Total: R$ ${order.totalPrice ?: 0}"

            // Informações do cliente
            textViewEmail.text = order.email ?: "Não informado"
            textViewPhone.text = order.phone ?: "Não informado"
            textViewZipCode.text = order.zipCode ?: "Não informado"
            textViewComplement.text = order.complement ?: "Não informado"
            textViewNumber.text = order.number ?: "Não informado"

            // Configuração inicial do Spinner
            val statusAdapter = ArrayAdapter(
                root.context,
                android.R.layout.simple_spinner_item,
                OrderStatus.values().map { it.name }
            )
            statusAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerOrderStatus.adapter = statusAdapter
            val statusIndex = OrderStatus.values().indexOf(order.status ?: OrderStatus.NOVO)
            spinnerOrderStatus.setSelection(statusIndex)
            spinnerOrderStatus.isEnabled = false

            // Configuração inicial dos botões
            resetEditingState(this, holder.itemView.context)

            // Lógica do botão "Editar"
            buttonEditOrder.setOnClickListener {
                enableEditingFields(this, holder.itemView.context)
            }

            // Lógica do botão "Salvar"
            buttonSaveOrder.setOnClickListener {
                val updatedOrder = order.copy(
                    email = textViewEmail.text.toString(),
                    phone = textViewPhone.text.toString(),
                    zipCode = textViewZipCode.text.toString(),
                    complement = textViewComplement.text.toString(),
                    number = textViewNumber.text.toString(),
                    status = spinnerOrderStatus.selectedItem.toString()
                )
                onOrderSave(updatedOrder)
                resetEditingState(this, holder.itemView.context)
                Toast.makeText(root.context, "Pedido alterado com sucesso!", Toast.LENGTH_SHORT).show()
            }

            // Expande ou colapsa ao clicar no título
            textViewOrderTitle.setOnClickListener {
                expandedOrderId = if (isExpanded) null else order.id
                notifyDataSetChanged()
                //WARNING
                onOrderClick(order.id!!)
            }
        }
    }

    override fun getItemCount(): Int = orders.size

    fun submitList(newOrders: List<Order>) {
        orders.clear()
        orders.addAll(newOrders)
        notifyDataSetChanged()
    }

    private fun enableEditingFields(binding: ItemOrderBinding, context: Context) {
        binding.textViewEmail.isEnabled = true
        binding.textViewPhone.isEnabled = true
        binding.textViewZipCode.isEnabled = true
        binding.textViewComplement.isEnabled = true
        binding.textViewNumber.isEnabled = true
        binding.spinnerOrderStatus.isEnabled = true

        binding.buttonSaveOrder.isEnabled = true
        binding.buttonSaveOrder.setTextColor(ContextCompat.getColor(context, R.color.black))

        binding.buttonEditOrder.isEnabled = false
        binding.buttonEditOrder.setTextColor(ContextCompat.getColor(context, R.color.light_gray))
    }

    private fun resetEditingState(binding: ItemOrderBinding, context: Context) {
        binding.textViewEmail.isEnabled = false
        binding.textViewPhone.isEnabled = false
        binding.textViewZipCode.isEnabled = false
        binding.textViewComplement.isEnabled = false
        binding.textViewNumber.isEnabled = false
        binding.spinnerOrderStatus.isEnabled = false

        binding.buttonSaveOrder.isEnabled = false
        binding.buttonSaveOrder.setTextColor(ContextCompat.getColor(context, R.color.light_gray))

        binding.buttonEditOrder.isEnabled = true
        binding.buttonEditOrder.setTextColor(ContextCompat.getColor(context, R.color.black))
    }

    inner class OrderViewHolder(val binding: ItemOrderBinding) : RecyclerView.ViewHolder(binding.root)
}
