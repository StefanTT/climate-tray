package io.github.thred.climatetray.util.prefs;

import java.util.UUID;

public abstract class AbstractTypedPrefs implements Prefs
{

    protected final String prefix;

    public AbstractTypedPrefs()
    {
        this(null);
    }

    public AbstractTypedPrefs(String prefix)
    {
        super();

        this.prefix = prefix;
    }

    @Override
    public final boolean existsChild(String key)
    {
        return containsLocalChild(prefix(prefix, key));
    }

    protected abstract boolean containsLocalChild(String key);

    @Override
    public final Prefs child(String key)
    {
        return localChild(prefix(prefix, key));
    }

    protected abstract Prefs localChild(String key);

    @Override
    public final boolean removeChild(String key)
    {
        if (!existsChild(key))
        {
            return false;
        }

        return removeLocalChild(prefix(prefix, key));
    }

    protected abstract boolean removeLocalChild(String key);

    @Override
    public final boolean exists(String key)
    {
        return containsLocal(prefix(prefix, key));
    }

    protected abstract boolean containsLocal(String key);

    @Override
    public final Integer getInteger(String key, Integer defaultValue)
    {
        if (!exists(key))
        {
            return defaultValue;
        }

        return getLocalInteger(prefix(prefix, key), defaultValue);
    }

    protected abstract Integer getLocalInteger(String key, Integer defaultValue);

    @Override
    public final boolean setInteger(String key, Integer value)
    {
        if (value == null)
        {
            return remove(key);
        }

        return setLocalInteger(prefix(prefix, key), value);
    }

    protected abstract boolean setLocalInteger(String key, Integer value);

    @Override
    public final Long getLong(String key, Long defaultValue)
    {
        if (!exists(key))
        {
            return defaultValue;
        }

        return getLocalLong(prefix(prefix, key), defaultValue);
    }

    protected abstract Long getLocalLong(String key, Long defaultValue);

    @Override
    public final boolean setLong(String key, Long value)
    {
        if (value == null)
        {
            return remove(key);
        }

        return setLocalLong(prefix(prefix, key), value);
    }

    protected abstract boolean setLocalLong(String key, Long value);

    @Override
    public final Float getFloat(String key, Float defaultValue)
    {
        if (!exists(key))
        {
            return defaultValue;
        }

        return getLocalFloat(prefix(prefix, key), defaultValue);
    }

    protected abstract Float getLocalFloat(String key, Float defaultValue);

    @Override
    public final boolean setFloat(String key, Float value)
    {
        if (value == null)
        {
            return remove(key);
        }

        return setLocalFloat(prefix(prefix, key), value);
    }

    protected abstract boolean setLocalFloat(String key, Float value);

    @Override
    public final Double getDouble(String key, Double defaultValue)
    {
        if (!exists(key))
        {
            return defaultValue;
        }

        return getLocalDouble(prefix(prefix, key), defaultValue);
    }

    protected abstract Double getLocalDouble(String key, Double defaultValue);

    @Override
    public final boolean setDouble(String key, Double value)
    {
        if (value == null)
        {
            return remove(key);
        }

        return setLocalDouble(prefix(prefix, key), value);
    }

    protected abstract boolean setLocalDouble(String key, Double value);

    @Override
    public final String getString(String key, String defaultValue)
    {
        if (!exists(key))
        {
            return defaultValue;
        }

        return getLocalString(prefix(prefix, key), defaultValue);
    }

    protected abstract String getLocalString(String key, String defaultValue);

    @Override
    public final boolean setString(String key, String value)
    {
        if (value == null)
        {
            return remove(key);
        }

        return setLocalString(prefix(prefix, key), value);
    }

    protected abstract boolean setLocalString(String key, String value);

    @Override
    public final Boolean getBoolean(String key, Boolean defaultValue)
    {
        if (!exists(key))
        {
            return defaultValue;
        }

        return getLocalBoolean(prefix(prefix, key), defaultValue);
    }

    protected abstract Boolean getLocalBoolean(String key, Boolean defaultValue);

    @Override
    public final boolean setBoolean(String key, Boolean value)
    {
        if (value == null)
        {
            return remove(key);
        }

        return setLocalBoolean(prefix(prefix, key), value);
    }

    protected abstract boolean setLocalBoolean(String key, Boolean value);

    @Override
    public final <TYPE extends Enum<TYPE>> TYPE getEnum(Class<TYPE> type, String key, TYPE defaultValue)
    {
        if (!exists(key))
        {
            return defaultValue;
        }

        return getLocalEnum(type, prefix(prefix, key), defaultValue);
    }

    protected abstract <TYPE extends Enum<TYPE>> TYPE getLocalEnum(Class<TYPE> type, String key, TYPE defaultValue);

    @Override
    public final <TYPE extends Enum<TYPE>> boolean setEnum(String key, TYPE value)
    {
        if (value == null)
        {
            return remove(key);
        }

        return setLocalEnum(prefix(prefix, key), value);
    }

    protected abstract <TYPE extends Enum<?>> boolean setLocalEnum(String key, TYPE value);

    @Override
    public UUID getUUID(String key, UUID defaultValue)
    {
        if (!exists(key))
        {
            return defaultValue;
        }

        return getLocalUUID(prefix(prefix, key), defaultValue);
    }

    protected abstract UUID getLocalUUID(String key, UUID defaultValue);

    @Override
    public boolean setUUID(String key, UUID value)
    {
        if (value == null)
        {
            return remove(key);
        }

        return setLocalUUID(prefix(prefix, key), value);
    }

    protected abstract boolean setLocalUUID(String key, UUID value);

    @Override
    public final boolean remove(String key)
    {
        if (!exists(key))
        {
            return false;
        }

        return removeLocal(prefix(prefix, key));
    }

    protected abstract boolean removeLocal(String key);

    protected static String prefix(String prefix, String key)
    {
        return (prefix != null) ? prefix + key : key;
    }

}
