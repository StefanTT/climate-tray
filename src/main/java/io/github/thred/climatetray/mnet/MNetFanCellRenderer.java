package io.github.thred.climatetray.mnet;

import io.github.thred.climatetray.ClimateTrayImageState;

import java.awt.Component;

import javax.swing.DefaultListCellRenderer;
import javax.swing.JList;

public class MNetFanCellRenderer extends DefaultListCellRenderer
{

    private static final long serialVersionUID = -1286708018290981191L;

    public MNetFanCellRenderer()
    {
        super();
    }

    @Override
    public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
        boolean cellHasFocus)
    {
        super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

        if (value == null)
        {
            setText("");

            return this;
        }

        MNetFan fan = (MNetFan) value;

        setIcon(fan.getImage().getIcon((isSelected) ? ClimateTrayImageState.SELECTED : ClimateTrayImageState.NOT_SELECTED, 16));
        setText(fan.getLabel());

        return this;
    }
}
