package br.com.ramires.gourment.coffemanagement

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.ramires.gourment.coffemanagement.data.repository.order.FirebaseOrderRepository
import br.com.ramires.gourment.coffemanagement.data.repository.order.MockOrderRepository
import br.com.ramires.gourment.coffemanagement.data.repository.order.OrderRepositoryInterface
import br.com.ramires.gourment.coffemanagement.data.repository.product.EmptyProductRepository
import br.com.ramires.gourment.coffemanagement.data.repository.product.FirebaseProductRepository
import br.com.ramires.gourment.coffemanagement.data.repository.product.MockProductRepository
import br.com.ramires.gourment.coffemanagement.data.repository.product.ProductRepositoryInterface
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

        val repositoryType = intent.getStringExtra("REPOSITORY_TYPE")
        initializeRepositories(repositoryType)

        setupTabLayoutStyle()
        setupTabListener()

        replaceFragment(ProductsFragment(productRepository!!))
    }

    private fun initializeRepositories(repositoryType: String?) {
        if (repositoryType == "MOCK") {
            productRepository = MockProductRepository()
            orderRepository = MockOrderRepository()
        } else {
            productRepository = FirebaseProductRepository()
            orderRepository = FirebaseOrderRepository()
        }
    }

    private fun setupTabLayoutStyle() {
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Produtos"))
        binding.tabLayout.addTab(binding.tabLayout.newTab().setText("Pedidos"))

        binding.tabLayout.getTabAt(0)?.view?.setBackgroundResource(R.drawable.tab_selected_background)
        binding.tabLayout.getTabAt(1)?.view?.setBackgroundResource(R.drawable.tab_default_background)
    }

    private fun setupTabListener() {
        binding.tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                tab?.view?.setBackgroundResource(R.drawable.tab_selected_background)
                when (tab?.position) {
                    0 -> replaceFragment(ProductsFragment(productRepository!!))
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
