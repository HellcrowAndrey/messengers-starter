package com.github.messengers.services;

import com.github.messengers.exceptions.SendSmsException;
import com.github.messengers.payloads.BroadcastMessage;
import com.github.messengers.payloads.BroadcastReport;
import com.github.messengers.payloads.MessageStatus;
import com.github.messengers.payloads.SingleMessage;
import com.infobip.ApiException;
import com.infobip.api.SendSmsApi;
import com.infobip.model.*;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
public class InfobipService {

    private final SendSmsApi smsSender;

    public Mono<BroadcastReport> sendSingleSms(SingleMessage payload) {
        return Mono.create(sink -> {
            try {
                SmsTextualMessage smsMessage = new SmsTextualMessage()
                        .from(payload.getFrom())
                        .addDestinationsItem(new SmsDestination().to(payload.getPhoneNumber()))
                        .language(new SmsLanguage().languageCode(payload.getLanguageCode()))
                        .transliteration(payload.getTransliteration())
                        .text(payload.getText());
                List<SmsResponseDetails> response = this.smsSender.sendSmsMessage(
                        new SmsAdvancedTextualRequest().messages(
                                Collections.singletonList(smsMessage)
                        )
                ).getMessages();
                if (Objects.nonNull(response) && !response.isEmpty()) {
                    SmsResponseDetails message = response.get(0);
                    sink.success(new BroadcastReport(
                            message.getTo(),
                            new MessageStatus(
                                    message.getStatus().getId(),
                                    message.getStatus().getGroupName())
                    ));
                }
            } catch (ApiException e) {
                sink.error(new SendSmsException(e.getMessage()));
            }
        });
    }

    public Flux<BroadcastReport> broadcastSms(BroadcastMessage payload) {
        return Flux.create(sink -> {
            try {
                List<SmsTextualMessage> messages = payload.getPhoneNumbers().stream()
                        .map(phone -> new SmsTextualMessage()
                                .from(payload.getFrom())
                                .addDestinationsItem(new SmsDestination().to(phone))
                                .language(new SmsLanguage().languageCode(payload.getLanguageCode()))
                                .transliteration(payload.getTransliteration())
                                .text(payload.getText())
                        ).collect(Collectors.toList());
                SmsResponse result = this.smsSender.sendSmsMessage(
                        new SmsAdvancedTextualRequest().messages(messages)
                );
                result.getMessages().stream().map(message -> new BroadcastReport(
                        message.getTo(),
                        new MessageStatus(
                                message.getStatus().getId(),
                                message.getStatus().getGroupName())
                )).forEach(sink::next);
                sink.complete();
            } catch (ApiException e) {
                sink.error(new SendSmsException(e.getMessage()));
            }
        });
    }

}
