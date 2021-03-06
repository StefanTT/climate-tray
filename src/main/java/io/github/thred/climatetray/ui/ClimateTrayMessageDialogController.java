/*
 * Copyright 2015 - 2018 Manfred Hantschel
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

import java.awt.Window;

import io.github.thred.climatetray.util.message.Message;

public class ClimateTrayMessageDialogController
    extends DefaultClimateTrayDialogController<Message, ClimateTrayMessageController>
{

    public static Button consumeOkDialog(Window owner, String title, Message message)
    {
        ClimateTrayMessageDialogController controller = new ClimateTrayMessageDialogController(owner, Button.OK);

        controller.setTitle(title);

        return controller.consume(message);
    }

    public static Button consumeRetryOkCancelDialog(Window owner, String title, Message message)
    {
        ClimateTrayMessageDialogController controller =
            new ClimateTrayMessageDialogController(owner, Button.RETRY, Button.OK, Button.CANCEL);

        controller.setTitle(title);

        return controller.consume(message);
    }

    public static Button consumeYesNoDialog(Window owner, String title, Message message)
    {
        ClimateTrayMessageDialogController controller =
            new ClimateTrayMessageDialogController(owner, Button.YES, Button.NO);

        controller.setTitle(title);

        return controller.consume(message);
    }

    public ClimateTrayMessageDialogController(Window owner, Button... buttons)
    {
        super(owner, new ClimateTrayMessageController(), buttons);
    }

}
