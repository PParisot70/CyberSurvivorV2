import Renderer.setBorder
import java.awt.*
import java.awt.event.*
import javax.swing.border.LineBorder


class MenuButton(text: String, x: Int, y: Int, width: Int, height: Int) : Button(text) {
    private var hover = false

    init {
        setBounds(x, y, width, height)
        setBackground(Color(199, 21, 133));
        setFont(Font("PixelArt", Font.PLAIN, 24));
        setBorder(LineBorder(Color.BLACK, 2, true))
        addMouseListener(object : MouseAdapter() {
            override fun mouseEntered(e: MouseEvent?) {
                hover = true
                setBounds(x - 10, y - 10, width + 20, height + 20)
            }

            override fun mouseExited(e: MouseEvent?) {
                hover = false
                setBounds(x, y, width, height)
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
            setBackground(Color(192, 92, 163));
            g?.drawRect(x,y , width + 20, height + 20)
        } else {
            setBackground(Color(199, 21, 133));
            g?.drawRect(x, y, width, height)
        }
    }
}