FROM node:18-alpine AS builder

WORKDIR /app

COPY package.json yarn.lock ./
COPY .yarn ./.yarn

RUN yarn install

COPY . .

RUN yarn build

FROM node:18-alpine AS runner

WORKDIR /app

RUN corepack enable && corepack prepare yarn@4.2.2 --activate

ENV NODE_ENV=production

COPY --from=builder /app/next.config.mjs ./
COPY --from=builder /app/.next ./.next
COPY --from=builder /app/.yarn ./.yarn
COPY --from=builder /app/.pnp.* ./
COPY --from=builder /app/yarn.lock ./
COPY --from=builder /app/package.json ./

EXPOSE 3000

CMD ["yarn", "start"]