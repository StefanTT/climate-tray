/*
 * Copyright 2015, 2016 Manfred Hantschel
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
package io.github.thred.climatetray.ui;

import java.util.List;

import io.github.thred.climatetray.mnet.MNetPreset;
import io.github.thred.climatetray.mnet.ui.MNetPresetCellRenderer;
import io.github.thred.climatetray.util.message.MessageBuffer;

public class ClimateTrayPresetSelectController extends AbstractClimateTraySelectController<MNetPreset>
{

    public ClimateTrayPresetSelectController()
    {
        super();

        list.setCellRenderer(new MNetPresetCellRenderer());
    }

    @Override
    public void prepareWith(List<MNetPreset> model)
    {
        super.prepareWith(model);

        clearSelection();
    }

    @Override
    public void modified(MessageBuffer messageBuffer)
    {
        super.modified(messageBuffer);

        if (listModel.getSize() == 0)
        {
            messageBuffer.info("You can add global presets for managing all air conditioners at once.");
        }
    }

    @Override
    protected String describe(MNetPreset element)
    {
        return element.describe();
    }

}
