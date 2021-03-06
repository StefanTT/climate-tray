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
package io.github.thred.climatetray.mnet;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.UUID;

import io.github.thred.climatetray.util.Copyable;
import io.github.thred.climatetray.util.Persistent;
import io.github.thred.climatetray.util.Utils;
import io.github.thred.climatetray.util.prefs.Prefs;

public class MNetDevice implements Copyable<MNetDevice>, Persistent
{

    private UUID id = UUID.randomUUID();
    private String name = "";
    private MNetInstallation installation = MNetInstallation.STANDING;
    private String host = "";
    private MNetEc ec = MNetEc.NONE;
    private Integer address = 0;
    private Integer group = null;
    private boolean selected = true;
    private boolean enabled = true;

    private MNetState state = new MNetState();
    private MNetPreset preset = new MNetPreset();

    private String model = null;

    public MNetDevice()
    {
        super();
    }

    public MNetDevice(UUID id, String name, MNetInstallation installation, String host, MNetEc ec, Integer address,
        Integer group, boolean selected, boolean enabled, MNetState state, MNetPreset preset, String model)
    {
        super();

        this.id = id;
        this.name = name;
        this.installation = installation;
        this.host = host;
        this.ec = ec;
        this.address = address;
        this.group = group;
        this.selected = selected;
        this.enabled = enabled;
        this.state = state;
        this.preset = preset;
        this.model = model;
    }

    @Override
    public MNetDevice deepCopy()
    {
        return new MNetDevice(id, name, installation, host, ec, address, group, selected, enabled,
            Copyable.deepCopy(state), Copyable.deepCopy(preset), model);
    }

    public UUID getId()
    {
        return id;
    }

    public void setId(UUID id)
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public MNetInstallation getInstallation()
    {
        return installation;
    }

    public void setInstallation(MNetInstallation installation)
    {
        this.installation = installation;
    }

    public MNetDeviceType getType()
    {
        return MNetDeviceType.INSTANCE;
    }

    public String getHost()
    {
        return host;
    }

    public void setHost(String host)
    {
        if (!Objects.equals(this.host, host))
        {
            this.host = host;
            group = null;
        }
    }

    public URL getURL() throws MalformedURLException
    {
        return MNetUtils.toURL(host);
    }

    public MNetEc getEc()
    {
        return ec;
    }

    public void setEc(MNetEc ec)
    {
        this.ec = ec;
    }

    public Integer getAddress()
    {
        return address;
    }

    public void setAddress(Integer address)
    {
        if (!Objects.equals(this.address, address))
        {
            this.address = address;
            group = null;
        }
    }

    public Integer getGroup()
    {
        return group;
    }

    public void setGroup(Integer group)
    {
        this.group = group;
    }

    public boolean isSelectedAndWorking()
    {
        return isSelected() && isWorking();
    }

    public boolean isWorking()
    {
        return getState() != null && getState().getFails() == 0;
    }

    public boolean isSelected()
    {
        return selected && enabled;
    }

    public void setSelected(boolean selected)
    {
        this.selected = selected;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }

    public MNetState getState()
    {
        return state;
    }

    public void setState(MNetState state)
    {
        this.state = state;
    }

    // currently not needed - later use: stores the defined preset, allows to detect changes by other apps
    @SuppressWarnings("unused")
    private MNetPreset getPreset()
    {
        return preset;
    }

    // currently not needed - later use: stores the defined preset, allows to detect changes by other apps
    @SuppressWarnings("unused")
    private void setPreset(MNetPreset preset)
    {
        this.preset = preset;
    }

    public String getModel()
    {
        return model;
    }

    public void setModel(String model)
    {
        this.model = model;
    }

    public String describeState()
    {
        return Utils.combine(": ", name, state.describe());
    }

    public String describeStateAction()
    {
        return Utils.combine(": ", name, state.describeAction());
    }

    public String describeSettings()
    {
        String target = host;

        if (!enabled)
        {
            target = "disabled";
        }
        else
        {
            try
            {
                URL url = getURL();

                if (url != null)
                {
                    target = url.toExternalForm();
                }
            }
            catch (MalformedURLException e)
            {
                // ignore
            }
        }

        return Utils
            .combine(": ", name,
                Utils.combine(" ", target, Utils.surround("[", Utils.combine(ec.getLabel(), address), "]")));
    }

    @Override
    public void read(Prefs prefs)
    {
        id = prefs.getUUID("id", id);
        name = prefs.getString("name", name);
        installation = prefs.getEnum(MNetInstallation.class, "installation", installation);
        host = prefs.getString("host", host);
        ec = prefs.getEnum(MNetEc.class, "ec", ec);
        address = prefs.getInteger("address", 0);
        group = null;
        selected = prefs.getBoolean("selected", selected);
        enabled = prefs.getBoolean("enabled", enabled);
        state = new MNetState();

        if (preset == null)
        {
            preset = new MNetPreset();
        }

        preset.read(prefs.withPrefix("preset."));
    }

    @Override
    public void write(Prefs prefs)
    {
        prefs.setUUID("id", id);
        prefs.setString("name", name);
        prefs.setEnum("installation", installation);
        prefs.setString("host", host);
        prefs.setEnum("ec", ec);
        prefs.setInteger("address", address);
        prefs.setBoolean("selected", selected);
        prefs.setBoolean("enabled", enabled);

        preset.write(prefs.withPrefix("preset."));
    }

    @Override
    public String toString()
    {
        return "MNetDevice [id="
            + id
            + ", name="
            + name
            + ", installation="
            + installation
            + ", host="
            + host
            + ", ec="
            + ec
            + ", address="
            + address
            + ", group="
            + group
            + ", selected="
            + selected
            + ", enabled="
            + enabled
            + ", state="
            + state
            + ", preset="
            + preset
            + ", model="
            + model
            + "]";
    }

}
