package via.sep3.viacalendar.startup;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.MockitoAnnotations;
import via.sep3.viacalendar.networking.handlers.ViaCalendarHandler;
import via.sep3.viacalendar.services.event.EventServiceDatabase;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServiceProviderTest {

    @Mock
    private EventServiceDatabase eventServiceDatabase;

    private ServiceProvider serviceProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        serviceProvider = new ServiceProvider();
    }

    @Test
    void testGetEventHandler_ReturnsSameInstance() {
        // Use MockedStatic to mock GlobalContext
        try (MockedStatic<GlobalContext> mockedGlobalContext = mockStatic(GlobalContext.class)) {
            // Arrange
            mockedGlobalContext.when(() -> GlobalContext.getBean(EventServiceDatabase.class))
                    .thenReturn(eventServiceDatabase);

            // Act
            ViaCalendarHandler handler1 = serviceProvider.getEventHandler();
            ViaCalendarHandler handler2 = serviceProvider.getEventHandler();

            // Assert
            assertNotNull(handler1);
            assertNotNull(handler2);
            assertSame(handler1, handler2, "Should return the same cached instance");
            
            // Verify GlobalContext.getBean was only called once (lazy initialization and caching)
            mockedGlobalContext.verify(() -> GlobalContext.getBean(EventServiceDatabase.class), times(1));
        }
    }
}
