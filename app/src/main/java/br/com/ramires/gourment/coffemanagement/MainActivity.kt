package br.com.ramires.gourment.coffemanagement

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import br.com.ramires.gourment.coffemanagement.databinding.ActivityMainBinding
import br.com.ramires.gourment.coffemanagement.ui.order.OrdersFragment
import br.com.ramires.gourment.coffemanagement.ui.product.ProductsFragment
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa com a tela de Produtos
        replaceFragment(ProductsFragment())

        // Configura o TabLayout
        binding.tabLayout.apply {
            // Adiciona as abas
            addTab(newTab().setText("Produtos"))
            addTab(newTab().setText("Pedidos"))

            // Aplica estilo inicial para todas as tabs
            for (i in 0 until tabCount) {
                val tab = getTabAt(i)
                tab?.view?.apply {
                    setBackgroundResource(R.drawable.tab_default)
                    layoutParams.height = 140 // Define altura maior
                }
                // Define cor de texto padrão (preta)
                val tabText = (tab?.view as? TextView)
                tabText?.setTextColor(resources.getColor(android.R.color.black))
            }

            // Configura o estilo inicial para a aba "Produtos"
            getTabAt(0)?.view?.apply {
                setBackgroundResource(R.drawable.tab_selected) // Fundo azul claro
                val tabText = (this as? TextView)
                tabText?.setTextColor(resources.getColor(android.R.color.black)) // Texto preto
            }

            // Listener para troca de fragmentos e estilo
            addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tab?.view?.setBackgroundResource(R.drawable.tab_selected) // Fundo azul claro
                    val tabText = (tab?.view as? TextView)
                    tabText?.setTextColor(resources.getColor(android.R.color.black)) // Texto preto
                    when (tab?.position) {
                        0 -> replaceFragment(ProductsFragment()) // Tela de Produtos
                        1 -> replaceFragment(OrdersFragment())  // Tela de Pedidos
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    tab?.view?.setBackgroundResource(R.drawable.tab_default) // Fundo branco
                    val tabText = (tab?.view as? TextView)
                    tabText?.setTextColor(resources.getColor(android.R.color.black)) // Texto preto
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
        }
    }

    // Método para trocar o fragmento
    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}
