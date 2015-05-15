package io.github.thred.climatetray;

import io.github.thred.climatetray.mnet.MNetDevice;
import io.github.thred.climatetray.mnet.MNetPreset;
import io.github.thred.climatetray.util.Persistent;
import io.github.thred.climatetray.util.TemperatureUnit;
import io.github.thred.climatetray.util.prefs.Prefs;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ClimateTrayPreferences implements Persistent
{

    private final List<MNetDevice> devices = new ArrayList<MNetDevice>();
    private final List<MNetPreset> presets = new ArrayList<MNetPreset>();

    private TemperatureUnit temperatureUnit = TemperatureUnit.CELSIUS;

    public ClimateTrayPreferences()
    {
        super();
    }

    public MNetDevice getDevice(UUID id)
    {
        return devices.stream().filter((device) -> Objects.equals(id, device.getId())).findFirst().orElse(null);
    }

    public List<MNetDevice> getDevices()
    {
        return devices;
    }

    public MNetPreset getPreset(UUID id)
    {
        return presets.stream().filter((device) -> Objects.equals(id, device.getId())).findFirst().orElse(null);
    }

    public List<MNetPreset> getPresets()
    {
        return presets;
    }

    public TemperatureUnit getTemperatureUnit()
    {
        return temperatureUnit;
    }

    public void setTemperatureUnit(TemperatureUnit temperatureUnit)
    {
        this.temperatureUnit = temperatureUnit;
    }

    @Override
    public void read(Prefs prefs)
    {
        Persistent.readList(prefs, "device", devices, () -> new MNetDevice());
        Persistent.readList(prefs, "preset", presets, () -> new MNetPreset());

        temperatureUnit = prefs.getEnum(TemperatureUnit.class, "temperatureUnit", temperatureUnit);
    }

    @Override
    public void write(Prefs prefs)
    {
        Persistent.writeList(prefs, "device", devices);
        Persistent.writeList(prefs, "preset", presets);

        prefs.setEnum("temperatureUnit", temperatureUnit);
    }

}
