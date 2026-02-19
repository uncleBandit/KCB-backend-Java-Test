package com.banking.masking;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "p11.masking")
public class MaskingProperties {

    private boolean enabled = true; // enable/disable masking
    private List<String> fields = new ArrayList<>(); // sensitive fields
    private MaskStyle maskStyle = MaskStyle.PARTIAL; // default style
    private char maskCharacter = '*'; // character to use for masking

    // getters and setters
    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public List<String> getFields() { return fields; }
    public void setFields(List<String> fields) { this.fields = fields; }

    public MaskStyle getMaskStyle() { return maskStyle; }
    public void setMaskStyle(MaskStyle maskStyle) { this.maskStyle = maskStyle; }

    public char getMaskCharacter() { return maskCharacter; }
    public void setMaskCharacter(char maskCharacter) { this.maskCharacter = maskCharacter; }

    public enum MaskStyle {
        FULL, PARTIAL, LAST4
    }
}