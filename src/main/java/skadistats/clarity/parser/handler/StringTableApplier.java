package skadistats.clarity.parser.handler;


import com.google.protobuf.ByteString;
import org.slf4j.LoggerFactory;
import skadistats.clarity.match.Match;
import skadistats.clarity.model.ModifierTableEntry;
import skadistats.clarity.parser.HandlerHelper;
import skadistats.clarity.wire.s1.proto.DotaModifiers.CDOTAModifierBuffTableEntry;

public enum StringTableApplier {

    DEFAULT {
        @Override
        public void apply(Match match, String tableName, int index, String key, ByteString value) {
            match.getStringTables().forName(tableName).set(index, key, value);
        }
    },
    MODIFIERS {
        @Override
        public void apply(Match match, String tableName, int index, String key, ByteString value) {
            try {
                if (value == null) {
                    return;   
                }
                CDOTAModifierBuffTableEntry message = CDOTAModifierBuffTableEntry.parseFrom(value);
                ModifierTableEntry mod = match.getModifiers().add(message);
                log.debug("{} {}",
                    match.getReplayTimeAsString(),
                    mod.toString()
                );
                
                HandlerHelper.traceMessage(log, match.getTick(), message);;
            } catch (Exception e) {
                log.error("can't parse modifier update");
                throw new RuntimeException(e);
            }
        }
    };
    
    private static final org.slf4j.Logger log = LoggerFactory.getLogger(StringTableApplier.class);
    
    public static StringTableApplier forName(String name) {
        if ("ActiveModifiers".equals(name)) {
            return MODIFIERS;
        } else {
            return DEFAULT;
        }
    }
    
    public abstract void apply(Match match, String tableName, int index, String key, ByteString value);
}
