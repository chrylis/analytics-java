package com.segment.analytics.messages;

import com.google.auto.value.AutoValue;
import com.google.common.collect.ImmutableMap;
import com.segment.analytics.internal.gson.AutoGson;
import java.util.Date;
import java.util.Map;
import java.util.UUID;
import javax.annotation.Nullable;

import static com.segment.analytics.internal.Utils.isNullOrEmpty;

/**
 * The screen call lets you record whenever a user sees a screen of your website, along with any
 * properties about the screen.
 * <p>
 * Use {@link #builder} to construct your own instances.
 *
 * @see <a href="https://segment.com/docs/spec/screen/">Screen</a>
 */
@AutoValue @AutoGson public abstract class ScreenMessage implements Message {

  /**
   * Start building an {@link AliasMessage} instance.
   *
   * @param name The name of the screen the user is on.
   * @throws IllegalArgumentException if the screen name is null or empty
   * @see <a href="https://segment.com/docs/spec/screen/#name">Name</a>
   */
  public static Builder builder(String name) {
    return new Builder(name);
  }

  @Nullable public abstract String name();

  @Nullable public abstract Map<String, Object> properties();

  public Builder toBuilder() {
    return new Builder(this);
  }

  /** Fluent API for creating {@link ScreenMessage} instances. */
  public static class Builder extends MessageBuilder<ScreenMessage, Builder> {
    private String name;
    private Map<String, Object> properties;

    private Builder(ScreenMessage screen) {
      super(screen);
      name = screen.name();
      properties = screen.properties();
    }

    private Builder(String name) {
      super(Type.SCREEN);
      if (isNullOrEmpty(name)) {
        throw new IllegalArgumentException("screen name cannot be null or empty.");
      }
      this.name = name;
    }

    /**
     * Set a map of information that describe the screen. These can be anything you want.
     *
     * @see <a href="https://segment.com/docs/spec/screen/#properties">Properties</a>
     */
    public Builder properties(Map<String, Object> properties) {
      if (properties == null) {
        throw new NullPointerException("Null properties");
      }
      this.properties = ImmutableMap.copyOf(properties);
      return this;
    }

    @Override Builder self() {
      return this;
    }

    @Override protected ScreenMessage realBuild(Type type, UUID messageId, Date timestamp,
        Map<String, Object> context, UUID anonymousId, String userId,
        Map<String, Object> integrations) {
      return new AutoValue_ScreenMessage(type, messageId, timestamp, context, anonymousId, userId,
          integrations, name, properties);
    }
  }
}
