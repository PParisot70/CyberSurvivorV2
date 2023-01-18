import java.awt.*
import java.awt.event.*

class MenuButton(text: String, x: Int, y: Int, width: Int, height: Int) : Button(text) {
    private var hover = false

    init {
        setBounds(x, y, width, height)
        setFont(Font("Arial", Font.PLAIN, 24))
        addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent?) {
                hover = true
               setBounds(x - 10, y - 10, width + 20, height + 20)

                println("IN")
            }

            override fun mouseExited(e: MouseEvent?) {
                hover = false
                setBounds(x, y, width, height)

                println("OUT")
            }
        })
    }

    fun setButtonAction(action: ActionListener) {
        addActionListener(action)
    }

    fun setText(text: String) {
        label = text
    }

    override fun paint(g: Graphics?) {
        super.paint(g)
        if (hover) {
            g?.color = Color.gray
            g?.drawRect(0, 0, width + 20, height + 20)
        } else {
            g?.color = Color.RED
           g?.drawRect(x, y, width, height)
        }
    }
}