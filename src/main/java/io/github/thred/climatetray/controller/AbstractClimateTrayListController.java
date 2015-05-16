/*
 * Copyright 2015 Manfred Hantschel
 * 
 * This file is part of Climate-Tray.
 * 
 * Climate-Tray is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 3 of the License, or any later version.
 * 
 * Climate-Tray is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with Climate-Tray. If not, see
 * <http://www.gnu.org/licenses/>.
 */
package io.github.thred.climatetray.controller;

import io.github.thred.climatetray.util.Copyable;
import io.github.thred.climatetray.util.MessageBuffer;
import io.github.thred.climatetray.util.swing.AdvancedListModel;
import io.github.thred.climatetray.util.swing.GBC;
import io.github.thred.climatetray.util.swing.SwingUtils;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public abstract class AbstractClimateTrayListController<TYPE extends Copyable<TYPE>> extends
    AbstractClimateTrayController<List<TYPE>, JPanel>
{

    protected final AdvancedListModel<TYPE> listModel = new AdvancedListModel<>();
    protected final JList<TYPE> list = monitor(new JList<TYPE>(listModel));
    protected final JButton addButton = SwingUtils.createButton("Add...", (e) -> add());
    protected final JButton editButton = SwingUtils.createButton("Edit...", (e) -> edit());
    protected final JButton removeButton = SwingUtils.createButton("Remove", (e) -> remove());
    protected final JButton upButton = SwingUtils.createButton("Up", (e) -> up());
    protected final JButton downButton = SwingUtils.createButton("Down", (e) -> down());
    protected final JPanel view = new JPanel(new GridBagLayout());

    private final MouseListener mouseListener = new MouseAdapter()
    {
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if (e.getClickCount() == 2)
            {
                edit();
            }
        }
    };

    public AbstractClimateTrayListController()
    {
        super();

        list.setPreferredSize(new Dimension(320, 64));
        list.setVisibleRowCount(5);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener((e) -> {
            if (!e.getValueIsAdjusting())
            {
                update();
            }
        });
        list.addMouseListener(mouseListener);

        GBC gbc = new GBC(2, 6).defaultOutsets(0, 0, 0, 0);

        view.add(new JScrollPane(list), gbc.span(1, 6).weight(1).fill());

        view.add(addButton, gbc.next().hFill().insetRight(0));
        view.add(editButton, gbc.next().hFill().insetRight(0));
        view.add(removeButton, gbc.next().hFill().insetRight(0));
        view.add(new JLabel(), gbc.next().weight(0, 1).insetRight(0));
        view.add(upButton, gbc.next().hFill().insetRight(0));
        view.add(downButton, gbc.next().hFill().insetRight(0));
    }

    @Override
    public JPanel getView()
    {
        return view;
    }

    @Override
    public void prepare(List<TYPE> model)
    {
        listModel.setList(Copyable.deepCopy(model));

        update();
    }

    @Override
    public void modified(MessageBuffer messageBuffer)
    {
        update();
    }

    @Override
    public void apply(List<TYPE> model)
    {
        model.clear();

        Copyable.deepCopy(listModel.getList(), model);
    }

    @Override
    public void dismiss(List<TYPE> model)
    {
        // intentionally left blank
    }

    public void refresh()
    {
        listModel.refreshAllElements();

        update();
    }

    public void update()
    {
        int selectedIndex = (list != null) ? list.getSelectedIndex() : -1;
        boolean anySelected = selectedIndex >= 0;

        addButton.setEnabled(true);
        editButton.setEnabled(anySelected);
        removeButton.setEnabled(anySelected);
        upButton.setEnabled(anySelected);
        downButton.setEnabled(anySelected);
    }

    public void add()
    {
        TYPE element = createElement();

        if (consumeElement(element))
        {
            listModel.addElement(element);
        }
    }

    protected abstract TYPE createElement();

    public void edit()
    {
        TYPE element = list.getSelectedValue();

        if (element == null)
        {
            return;
        }

        if (consumeElement(element))
        {
            listModel.refreshElement(element);
        }
    }

    protected abstract boolean consumeElement(TYPE element);

    public void remove()
    {
        int selectedIndex = list.getSelectedIndex();

        if (selectedIndex < 0)
        {
            return;
        }

        listModel.removeElementAt(selectedIndex);

        if (selectedIndex >= listModel.getSize())
        {
            selectedIndex = listModel.getSize() - 1;
        }

        if (selectedIndex >= 0)
        {
            list.setSelectedIndex(selectedIndex);
        }
    }

    public void up()
    {
        int selectedIndex = list.getSelectedIndex();

        if (selectedIndex < 1)
        {
            return;
        }

        listModel.moveElementAt(selectedIndex, selectedIndex - 1);
        list.setSelectedIndex(selectedIndex - 1);
    }

    public void down()
    {
        int selectedIndex = list.getSelectedIndex();

        if ((selectedIndex < 0) || (selectedIndex >= (listModel.getSize() - 1)))
        {
            return;
        }

        listModel.moveElementAt(selectedIndex, selectedIndex + 1);
        list.setSelectedIndex(selectedIndex + 1);
    }

}
