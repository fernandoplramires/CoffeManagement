package br.com.ramires.gourment.coffemanagement

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.ramires.gourment.coffemanagement.data.repository.*
import br.com.ramires.gourment.coffemanagement.databinding.ActivityMainBinding
import br.com.ramires.gourment.coffemanagement.ui.order.OrdersFragment
import br.com.ramires.gourment.coffemanagement.ui.product.ProductsFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    private var productRepository: ProductRepositoryInterface? = null
    private var orderRepository: OrderRepositoryInterface? = null
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        promptUserForRepository()

        setupTabLayoutStyle()
        setupTabListener()

        replaceFragment(ProductsFragment())
    }

    private fun setupTabLayoutStyle() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Produtos"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Pedidos"))

        binding.tabLayout.getTabAt(0)?.view?.setBackgroundResource(R.drawable.tab_selected_background)
        binding.tabLayout.getTabAt(1)?.view?.setBackgroundResource(R.drawable.tab_default_background)
    }

    private fun promptUserForRepository() {
        val options = arrayOf("Mock Repository", "Real Repository")
        AlertDialog.Builder(this)
            .setTitle("Escolha o Tipo de Repositório")
            .setSingleChoiceItems(options, -1) { dialog, which ->
                // Escolha do repositório
                if (which == 0) {
                    productRepository = MockProductRepository()
                    orderRepository = MockOrderRepository()
                } else {
                    productRepository = ProductRepository()
                    orderRepository = OrderRepository()
                }
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    private fun setupTabListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.view?.setBackgroundResource(R.drawable.tab_selected_background)
                when (tab?.position) {
                    0 -> replaceFragment(ProductsFragment())
                    1 -> replaceFragment(OrdersFragment(orderRepository!!))
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                tab?.view?.setBackgroundResource(R.drawable.tab_default_background)
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Não faz nada no momento
            }
        })
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
